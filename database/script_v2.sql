-- =============================================================================
-- DỰ ÁN ACCESSHUB - SCRIPT TẠO DATABASE (POSTGRESQL DDL)
-- =============================================================================

-- Tách riêng bảng master permissions ra đầu để không bị lỗi thứ tự tạo bảng
-- Khởi tạo các bảng chính trước
CREATE TYPE status_type AS ENUM ('ACTIVE', 'INACTIVE', 'DELETED');

-- 1. Bảng Phòng ban
CREATE TABLE departments (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dept_code VARCHAR(100) UNIQUE NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP null,
    CONSTRAINT chk_dept_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'DELETED'))
);

-- 2. Bảng Nhân sự
CREATE TABLE users (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dept_id INT,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP null
);

-- 3. Bảng Dự án vệ tinh
CREATE TABLE projects (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    prj_code VARCHAR(100) UNIQUE NOT NULL,
    prj_name VARCHAR(255) NOT NULL,
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL
);

-- 4. Bảng Menu (Hỗ trợ cấu hình đa cấp self-reference)
CREATE TABLE menus (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id INT not null,
    parent_id INT REFERENCES menus(id) ON DELETE SET NULL,
    ui_code VARCHAR(50) GENERATED ALWAYS AS ('ui_' || id::text) STORED,
    url VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    details TEXT,
    sort_order INT DEFAULT 0,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL
);

-- 5. Bảng Vai trò (Roles)
CREATE TABLE roles (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_code VARCHAR(50) unique NOT null,
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL
);

-- 6. Bảng Hành động Master (Permissions)
CREATE TABLE permissions (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pers_code VARCHAR(50) UNIQUE NOT NULL, -- VD: CREATE, READ, UPDATE, DELETE, EXPORT...
    details TEXT,
    status status_type DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL
);


-- =============================================================================
-- CÁC BẢNG TRUNG GIAN (JUNCTION TABLES) VỚI KHÓA CHÍNH TỔ HỢP
-- =============================================================================

-- 7. Bảng nối tương tác Nhiều - Nhiều giữa Người dùng và Vai trò
CREATE TABLE group_roles (
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

-- 8. Ý định 1: Định nghĩa Menu này chứa các hành động hợp lệ nào
CREATE TABLE menu_permissions (
    menu_id INT REFERENCES menus(id) ON DELETE CASCADE,
    permission_id INT REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (menu_id, permission_id)
);

-- 9. Ý định 2: Phân quyền thực tế cho Role trên từng Menu (Bật/Tắt từ danh sách trên)
CREATE TABLE role_menu_permissions (
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    menu_id INT NOT NULL,
    permission_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id, menu_id, permission_id)
);


-- =============================================================================
-- TẠO CÁC INDEX BỔ TRỢ ĐỂ TỐI ƯU TỐC ĐỘ TRUY VẤN BIỆT LẬP (SOFT DELETE)
-- =============================================================================
-- Giúp Postgres lọc cực nhanh những bản ghi chưa bị xóa mềm (deleted_at IS NULL)
CREATE INDEX idx_users_active_lookup ON users(username) WHERE deleted_at IS NULL;
CREATE INDEX idx_projects_code_lookup ON projects(prj_code) WHERE deleted_at IS NULL;
CREATE INDEX idx_menus_project_lookup ON menus(project_id) WHERE deleted_at IS NULL;