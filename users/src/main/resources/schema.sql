CREATE TABLE IF NOT EXISTS _user (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    city VARCHAR(255),
    role ENUM('USER', 'ADMIN', 'MANAGER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS _token (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      token VARCHAR(255) NOT NULL,
    tokenType ENUM('BEARER') NOT NULL DEFAULT 'BEARER',
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE
    );
