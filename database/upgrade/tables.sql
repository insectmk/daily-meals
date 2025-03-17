-- 每日饭菜-用户
CREATE TABLE IF NOT EXISTS `meals_user`
(
    `id`                BIGINT        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `mobile`            VARCHAR(11)   NULL     DEFAULT NULL COMMENT '手机号',
    `password`          VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '密码',
    `status`            TINYINT       NOT NULL COMMENT '状态',
    `register_ip`       VARCHAR(32)   NOT NULL COMMENT '注册 IP',
    `register_terminal` TINYINT       NULL     DEFAULT NULL COMMENT '注册终端',
    `login_ip`          VARCHAR(50)   NULL     DEFAULT '' COMMENT '最后登录IP',
    `login_date`        DATETIME      NULL     DEFAULT NULL COMMENT '最后登录时间',
    `nickname`          VARCHAR(30)   NOT NULL DEFAULT '' COMMENT '用户昵称',
    `avatar`            VARCHAR(512)  NOT NULL DEFAULT '' COMMENT '头像',
    `name`              VARCHAR(30)   NULL     DEFAULT '' COMMENT '真实名字',
    `gender`            TINYINT       NULL     DEFAULT 0 COMMENT '用户性别',
    `area_id`           BIGINT        NULL     DEFAULT NULL COMMENT '所在地',
    `birthday`          DATETIME      NULL     DEFAULT NULL COMMENT '出生日期',
    `tag_ids`           VARCHAR(255)  NULL     DEFAULT NULL COMMENT '用户标签编号列表，以逗号分隔',
    `memo`              VARCHAR(2000) NULL     DEFAULT NULL COMMENT '备注',
    `creator`           VARCHAR(64)   NULL     DEFAULT '' COMMENT '创建者',
    `create_time`       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           VARCHAR(64)   NULL     DEFAULT '' COMMENT '更新者',
    `update_time`       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           BIT(1)        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         BIGINT        NOT NULL DEFAULT 0 COMMENT '租户编号',
    CONSTRAINT `pk_meals_user_id` PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  default CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '每日饭菜用户';

-- 菜谱表
CREATE TABLE IF NOT EXISTS `meals_recipe`
(
    `id`           bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`      bigint   NOT NULL COMMENT '用户编号',
    `name`         varchar(255)                                                 DEFAULT NULL COMMENT '名称',
    `recipe_desc`  varchar(512)                                                 DEFAULT NULL COMMENT '简介',
    `recipe_step`  text                                                         DEFAULT NULL COMMENT '教程',
    `recipe_type`  tinyint                                                      DEFAULT NULL COMMENT '菜谱类型',
    `recipe_tag`   tinyint                                                      DEFAULT NULL COMMENT '标签',
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
    CONSTRAINT `pk_meals_recipe_id` PRIMARY KEY (`id`) using btree,
    -- 用户外键：删除更新
    CONSTRAINT `fk_meals_recipe_user_id` FOREIGN KEY (`user_id`)
        REFERENCES `meals_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
    CONSTRAINT `pk_meals_recipe_food_id` PRIMARY KEY (`id`) using btree,
    -- 食谱外键：删除、更新
    CONSTRAINT `fk_meals_recipe_food_recipe_id` FOREIGN KEY (`recipe_id`)
        REFERENCES `meals_recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    -- 食材外键：删除、更新
    CONSTRAINT `fk_meals_recipe_food_food_id` FOREIGN KEY (`food_id`)
        REFERENCES `meals_food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱食材表，储存菜谱的食材信息';

-- 菜谱计划
CREATE TABLE IF NOT EXISTS `meals_daily_plan`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`     bigint   NOT NULL COMMENT '用户编号',
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
    CONSTRAINT `pk_meals_daily_plan_id` PRIMARY KEY (`id`) using btree,
    -- 用户外键：删除更新
    CONSTRAINT `fk_meals_daily_plan_user_id` FOREIGN KEY (`user_id`)
        REFERENCES `meals_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    -- 食谱外键：删除、更新
    CONSTRAINT `fk_meals_daily_plan_recipe_id` FOREIGN KEY (`recipe_id`)
        REFERENCES `meals_recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱计划表，储存菜谱计划信息';
