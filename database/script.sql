-- =============================================================================
-- DỰ ÁN ACCESSHUB - SCRIPT TẠO DATABASE (POSTGRESQL DDL) - REVISED
-- Theo 3 bounded context đã chốt: Auth (token/session - quản lý riêng, KHÔNG
-- có trong file này) / Organization / Project
-- =============================================================================

-- 0. Type dùng chung
-- Bỏ 'DELETED' khỏi enum: soft-delete chỉ dùng cột deleted_at làm nguồn sự
-- thật duy nhất, tránh 2 cờ xóa mềm lệch nhau (status vs deleted_at).
CREATE TYPE status_type AS ENUM ('ACTIVE', 'INACTIVE');


-- =============================================================================
-- CONTEXT: ORGANIZATION (departments, users, group_roles)
-- =============================================================================

-- 1. Phòng ban
CREATE TABLE departments (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dept_code VARCHAR(100) UNIQUE NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMPTZ NULL
);

-- 2. Nhân sự (Users)
-- departments & users cùng nằm trong Organization context (intra-context)
-- nên dùng FK cứng để đảm bảo integrity, không cần liên kết yếu qua code nữa
-- (liên kết yếu chỉ có ý nghĩa khi cắt ngang biên context, không phải trong context).
CREATE TABLE users (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dept_id INT REFERENCES departments(id) ON DELETE SET NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMPTZ NULL
);


-- =============================================================================
-- CONTEXT: PROJECT (projects, roles, menus, permissions + junction nội bộ)
-- =============================================================================

-- 3. Dự án vệ tinh
CREATE TABLE projects (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    prj_code VARCHAR(100) UNIQUE NOT NULL,
    prj_name VARCHAR(255) NOT NULL,
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMPTZ NULL
);

-- 4. Vai trò (Roles) - thuộc về 1 project cụ thể. 2 project có thể có role
-- trùng tên (vd "ADMIN") mà vẫn là 2 entity độc lập. roles & projects cùng
-- nằm trong Project context nên FK cứng + CASCADE là đúng (intra-context).
CREATE TABLE roles (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id INT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    role_code VARCHAR(50) NOT NULL,
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMPTZ NULL,
    CONSTRAINT uq_project_role_code UNIQUE (project_id, role_code)
);

-- 5. Menu (hỗ trợ cấu hình đa cấp self-reference)
CREATE TABLE menus (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id INT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    parent_id INT NULL REFERENCES menus(id) ON DELETE CASCADE,
    ui_code VARCHAR(50) UNIQUE NOT NULL,
    url VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    details TEXT,
    sort_order INT DEFAULT 0,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMPTZ NULL
);

-- 6. Hành động Master (Permissions) - dữ liệu tham chiếu dùng chung,
-- không scope theo project (CREATE/READ/UPDATE/DELETE... áp dụng mọi nơi).
CREATE TABLE permissions (
    id INT PRIMARY KEY,
    pers_code VARCHAR(50) UNIQUE NOT NULL,
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMPTZ NULL
);

-- 7. Menu chứa các hành động hợp lệ nào (junction nội bộ Project context,
-- FK cứng OK vì 2 bảng đều thuộc Project).
CREATE TABLE menu_permissions (
    menu_id INT REFERENCES menus(id) ON DELETE CASCADE,
    permission_id INT REFERENCES permissions(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    PRIMARY KEY (menu_id, permission_id)
);

-- 8. Phân quyền thực tế cho Role trên từng Menu (junction nội bộ Project,
-- FK cứng OK vì roles/menus/permissions đều thuộc Project context).
CREATE TABLE role_menu_permissions (
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    menu_id INT REFERENCES menus(id) ON DELETE CASCADE,
    permission_id INT REFERENCES permissions(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    PRIMARY KEY (role_id, menu_id, permission_id)
);


-- =============================================================================
-- JUNCTION XUYÊN BIÊN CONTEXT: ORGANIZATION <-> PROJECT
-- role_id KHÔNG FK cứng tới roles, vì roles thuộc Project context còn
-- group_roles thuộc Organization context. Đánh đổi: mất CASCADE tự động khi
-- role bị xóa ở Project context -> cần job dọn rác định kỳ hoặc event/callback
-- từ Project context sang Organization context khi 1 role bị xóa/deactivate.
-- =============================================================================

-- 9. Gán Role cho User
CREATE TABLE group_roles (
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    role_id INT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    PRIMARY KEY (user_id, role_id)
);


-- =============================================================================
-- INDEX BỔ TRỢ
-- =============================================================================

-- Lookup user khi login/check quyền
CREATE INDEX idx_users_active_lookup ON users(username) WHERE deleted_at IS NULL;

-- Lookup project theo code
CREATE INDEX idx_projects_code_lookup ON projects(prj_code) WHERE deleted_at IS NULL;

-- Lookup menu theo project
CREATE INDEX idx_menus_project_lookup ON menus(project_id) WHERE deleted_at IS NULL;

-- Tối ưu truy vấn đệ quy cây menu (CTE Recursive)
CREATE INDEX idx_menus_parent ON menus(parent_id) WHERE deleted_at IS NULL;

-- role_id ở group_roles không còn FK cứng -> cần index riêng để lookup nhanh
-- "user nào đang giữ role X", và hỗ trợ job dọn rác khi role bị xóa ở Project context.
CREATE INDEX idx_group_roles_role_id ON group_roles(role_id);


INSERT INTO permissions
(
    id,
    pers_code,
    details,
    status,
    created_at,
    created_by,
    updated_at,
    updated_by,
    deleted_at
)
VALUES
(1,  'VIEW',             'View',             'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(2,  'CREATE',           'Create',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(3,  'UPDATE',           'Update',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(4,  'DELETE',           'Delete',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(5,  'GRANT_PERMISSION', 'Grant permission', 'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(6,  'EXPORT',           'Export',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(7,  'IMPORT',           'Import',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(8,  'CANCEL',           'Cancel',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(9,  'APPROVE',          'Approve',          'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(10, 'REJECT',           'Reject',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(11, 'REVERT',           'Revert',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(12, 'ASSIGN',           'Assign',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(13, 'ANALYZE',          'Analyze',          'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(14, 'DOWNLOAD',         'Download',         'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(15, 'PROCESS',          'Process',          'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(16, 'RESOLVE',          'Resolve',          'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(17, 'CLOSE',            'Close',            'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL),
(18, 'UPLOAD',           'Upload',           'ACTIVE', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', NULL);
