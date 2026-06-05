-- =============================================================================
-- DỰ ÁN ACCESSHUB - SCRIPT TẠO DATABASE (POSTGRESQL DDL)
-- =============================================================================

-- Tách riêng bảng master permissions ra đầu để không bị lỗi thứ tự tạo bảng
-- Khởi tạo các bảng chính trước

-- 1. Bảng Phòng ban
CREATE TABLE departments (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dept_code VARCHAR(100) UNIQUE NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    details TEXT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL
);

-- 2. Bảng Nhân sự
CREATE TABLE users (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dept_id INT REFERENCES departments(id),
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL
);

-- 3. Bảng Dự án vệ tinh
CREATE TABLE projects (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,
    url VARCHAR(255) UNIQUE,
    name VARCHAR(255) NOT NULL,
    details TEXT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL
);

-- 4. Bảng Menu (Hỗ trợ cấu hình đa cấp self-reference)
CREATE TABLE menus (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id INT REFERENCES projects(id) ON DELETE CASCADE,
    parent_id INT REFERENCES menus(id) ON DELETE SET NULL,
    url VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    details TEXT,
    sort_order INT DEFAULT 0,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL
);

-- 5. Bảng Vai trò (Roles)
CREATE TABLE roles (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id INT REFERENCES projects(id) ON DELETE CASCADE,
    role_code VARCHAR(100) NOT NULL,
    details TEXT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP NULL,
    -- Tránh trùng lặp mã role trong cùng một dự án
    CONSTRAINT unique_project_role UNIQUE (project_id, role_code)
);

-- 6. Bảng Hành động Master (Permissions)
CREATE TABLE permissions (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL, -- VD: CREATE, READ, UPDATE, DELETE, EXPORT...
    details TEXT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
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

    PRIMARY KEY (role_id, menu_id, permission_id),
    CONSTRAINT fk_role_menu_permission_valid
        FOREIGN KEY (menu_id, permission_id)
        REFERENCES menu_permissions(menu_id, permission_id)
        ON DELETE CASCADE
);


-- =============================================================================
-- TẠO CÁC INDEX BỔ TRỢ ĐỂ TỐI ƯU TỐC ĐỘ TRUY VẤN BIỆT LẬP (SOFT DELETE)
-- =============================================================================
-- Giúp Postgres lọc cực nhanh những bản ghi chưa bị xóa mềm (deleted_at IS NULL)
CREATE INDEX idx_users_active_lookup ON users(username) WHERE deleted_at IS NULL;
CREATE INDEX idx_projects_code_lookup ON projects(code) WHERE deleted_at IS NULL;
CREATE INDEX idx_menus_project_lookup ON menus(project_id) WHERE deleted_at IS NULL;