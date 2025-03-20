-- 菜谱表
CREATE TABLE IF NOT EXISTS `meals_recipe`
(
    `id`           bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`      bigint                                                       DEFAULT NULL COMMENT '用户编号',
    `name`         varchar(255)                                                 DEFAULT NULL COMMENT '名称',
    `recipe_desc`  varchar(512)                                                 DEFAULT NULL COMMENT '简介',
    `recipe_step`  text                                                         DEFAULT NULL COMMENT '教程',
    `recipe_type`  tinyint                                                      DEFAULT NULL COMMENT '菜谱类型',
    `recipe_level` tinyint                                                      DEFAULT '0' COMMENT '烹饪难度',
    `sort`         int                                                          DEFAULT '0' COMMENT '排序',
    `memo`         varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `status`       tinyint  NOT NULL COMMENT '状态',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`  datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`  datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_recipe_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱表，储存菜谱信息';

-- 食材表
CREATE TABLE IF NOT EXISTS `meals_food`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(255)                                                 DEFAULT NULL COMMENT '名称',
    `food_type`   tinyint                                                      DEFAULT NULL COMMENT '分类',
    `food_unit`   tinyint                                                      DEFAULT NULL COMMENT '单位',
    `memo`        varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_food_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='食材表，储存原料信息';

-- 菜谱食材表
CREATE TABLE IF NOT EXISTS `meals_recipe_food`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `recipe_id`   bigint                                                       DEFAULT NULL COMMENT '菜谱ID',
    `food_id`     bigint                                                       DEFAULT NULL COMMENT '食材ID',
    `amount`      float                                                        DEFAULT NULL COMMENT '量',
    `memo`        varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_recipe_food_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱食材表，储存菜谱的食材信息';

-- 菜谱计划
CREATE TABLE IF NOT EXISTS `meals_daily_plan`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`     bigint                                                       DEFAULT NULL COMMENT '用户编号',
    `recipe_id`   bigint                                                       DEFAULT NULL COMMENT '菜谱ID',
    `plan_date`   date                                                         DEFAULT NULL COMMENT '计划日',
    `meal_type`   tinyint                                                      DEFAULT NULL COMMENT '餐次类型',
    `memo`        varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_daily_plan_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱计划表，储存菜谱计划信息';
