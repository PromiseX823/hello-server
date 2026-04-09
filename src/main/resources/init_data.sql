-- =============================================
-- 先清空表（可选，用于重置数据，避免冲突）
-- =============================================
TRUNCATE TABLE sys_user RESTART IDENTITY CASCADE;
TRUNCATE TABLE user_info RESTART IDENTITY;

-- =============================================
-- 任务5：sys_user 表批量插入数据（标准正确语法）
-- =============================================
INSERT INTO sys_user (username, password)
SELECT
    'user_' || LPAD(generate_series(1, 50)::TEXT, 2, '0'),
    '123456'
ON CONFLICT (username) DO NOTHING;

-- =============================================
-- 任务6：user_info 表批量插入数据（一对一关联sys_user）
-- =============================================
INSERT INTO user_info (real_name, phone, address, user_id)
SELECT
    '用户' || LPAD(generate_series(1, 50)::TEXT, 2, '0'),
    '138' || LPAD((10000000 + generate_series(1, 50))::TEXT, 8, '0'),
    '中国XX省XX市XX区' || generate_series(1, 50) || '号',
    generate_series(1, 50)
ON CONFLICT (user_id) DO NOTHING;

-- =============================================
-- 验证：立即查询数据，确认插入成功

-- =============================================
SELECT 'sys_user 数据量：' || COUNT(*) AS check_result FROM sys_user;
SELECT 'user_info 数据量：' || COUNT(*) AS check_result FROM user_info;

-- 查看前10条数据，验证内容正确
SELECT * FROM sys_user ORDER BY id LIMIT 10;
SELECT * FROM user_info ORDER BY user_id LIMIT 10;

