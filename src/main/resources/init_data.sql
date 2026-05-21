--CREATE DATABASE user_db;

CREATE TABLE IF NOT EXISTS sys_user (
                                        id SERIAL PRIMARY KEY,          -- 自增主键
                                        username VARCHAR(50) NOT NULL UNIQUE,  -- 用户名唯一
    password VARCHAR(100) NOT NULL  -- 密码
    );

CREATE TABLE IF NOT EXISTS user_info (
                                         id SERIAL PRIMARY KEY,
                                         real_name VARCHAR(50),
    phone VARCHAR(20),
    address VARCHAR(255),
    user_id INT NOT NULL UNIQUE,     -- 关联用户ID，一对一唯一
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE
    );

TRUNCATE TABLE sys_user RESTART IDENTITY CASCADE;
TRUNCATE TABLE user_info RESTART IDENTITY;

INSERT INTO sys_user (username, password)
SELECT
    'user_' || LPAD(generate_series(1, 50)::TEXT, 2, '0'),
    '123456'
    ON CONFLICT (username) DO NOTHING;

INSERT INTO user_info (real_name, phone, address, user_id)
SELECT
    '用户' || LPAD(generate_series(1, 50)::TEXT, 2, '0'),
    '138' || LPAD((10000000 + generate_series(1, 50))::TEXT, 8, '0'),
    '中国XX省XX市XX区' || generate_series(1, 50) || '号',
    generate_series(1, 50)
    ON CONFLICT (user_id) DO NOTHING;

SELECT 'sys_user 数据量：' || COUNT(*) AS check_result FROM sys_user;
SELECT 'user_info 数据量：' || COUNT(*) AS check_result FROM user_info;

SELECT * FROM sys_user ORDER BY id LIMIT 10;
SELECT * FROM user_info ORDER BY user_id LIMIT 10;


CREATE TABLE document_chunk (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                document_id BIGINT NOT NULL,
                                chunk_index INT NOT NULL,
                                chunk_content TEXT NOT NULL,
                                create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                INDEX idx_document_id (document_id)
);