# 数据库说明



## 建库

```sql
-- 创建数据库 daily_meals，字符集为 utf8mb4
CREATE DATABASE IF NOT EXISTS daily_meals CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户 daily_meals，密码为 douban_crawler
CREATE USER 'daily_meals'@'%' IDENTIFIED BY 'daily_meals';

-- 授予 daily_meals 用户对 douban_crawler 数据库的所有权限
GRANT ALL PRIVILEGES ON daily_meals.* TO 'daily_meals'@'%';

-- 刷新权限
FLUSH PRIVILEGES;
```

## 表名规范

1. 使用下划线+小写字母
2. 视图使用v_开头