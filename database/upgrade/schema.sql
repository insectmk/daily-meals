DROP PROCEDURE IF EXISTS schema_update; -- 删除已经存在的同名存储过程
CREATE PROCEDURE schema_update() BEGIN
-- ************开始**************

-- ************结束**************
END;
call schema_update();-- 运行该存储过程
drop PROCEDURE schema_update; -- 删除该存储过程
