-- 菜谱表
CREATE TABLE IF NOT EXISTS `meals_recipe`
(
    `id`              bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`         bigint                                                       DEFAULT NULL COMMENT '用户编号',
    `name`            varchar(255)                                                 DEFAULT NULL COMMENT '名称',
    `recipe_desc`     varchar(512)                                                 DEFAULT NULL COMMENT '简介',
    `recipe_step`     text                                                         DEFAULT NULL COMMENT '教程',
    `recipe_type`     tinyint                                                      DEFAULT NULL COMMENT '菜谱类型',
    `recipe_category` varchar(2000)                                                DEFAULT NULL COMMENT '菜谱分类',
    `recipe_level`    tinyint                                                      DEFAULT '0' COMMENT  '烹饪难度',
    `sort`            int                                                          DEFAULT '0' COMMENT  '排序',
    `pic_url`         varchar(256)                                                 DEFAULT NULL COMMENT '菜谱封面图',
    `slider_pic_urls` varchar(2000)                                                DEFAULT NULL COMMENT '菜谱轮播图',
    `memo`            varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `status`          tinyint  NOT NULL COMMENT '状态',
    `creator`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`     datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`     datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`       bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_recipe_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱表，储存菜谱信息';

-- 食材表
CREATE TABLE IF NOT EXISTS `meals_food`
(
    `id`            bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`          varchar(255)                                                 DEFAULT NULL COMMENT '名称',
    `food_type`     tinyint                                                      DEFAULT NULL COMMENT '类型',
    `food_category` varchar(2000)                                                DEFAULT NULL COMMENT '食材分类',
    `food_unit`     tinyint                                                      DEFAULT NULL COMMENT '单位',
    `memo`          varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `creator`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`   datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`   datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`     bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
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
    `food_name`   varchar(256)                                                 DEFAULT NULL COMMENT '食材名称',
    `amount`      float                                                        DEFAULT NULL COMMENT '量',
    `food_unit`   varchar(20)                                                  DEFAULT NULL COMMENT '量单位',
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

-- 每日计划
CREATE TABLE IF NOT EXISTS `meals_daily_plan`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`     bigint                                                       DEFAULT NULL COMMENT '用户编号',
    `plan_date`   date                                                         DEFAULT NULL COMMENT '计划日',
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
  COLLATE = utf8mb4_unicode_ci COMMENT ='每日计划表，储存计划基本信息';

-- 每日计划明细
CREATE TABLE IF NOT EXISTS `meals_daily_plan_item`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `plan_id`     bigint                                                       default null comment '计划ID',
    `recipe_id`   bigint                                                       DEFAULT NULL COMMENT '菜谱ID',
    `meal_type`   tinyint                                                      DEFAULT NULL COMMENT '餐次类型',
    `memo`        varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_daily_plan_item_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='每日计划明细表，储存菜谱计划信息';

-- 菜谱分类
CREATE TABLE IF NOT EXISTS `meals_recipe_category`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '分类编号',
    `parent_id`   bigint                                                        NOT NULL COMMENT '父分类编号',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
    `pic_url`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '移动端分类图',
    `sort`        int                                                                    DEFAULT '0' COMMENT '分类排序',
    `status`      tinyint                                                       NOT NULL COMMENT '开启状态',
    `memo`        varchar(2000)                                                          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_recipe_category_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱分类';

-- 食材分类
CREATE TABLE IF NOT EXISTS `meals_food_category`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '分类编号',
    `parent_id`   bigint                                                        NOT NULL COMMENT '父分类编号',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
    `pic_url`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '移动端分类图',
    `sort`        int                                                                    DEFAULT '0' COMMENT '分类排序',
    `status`      tinyint                                                       NOT NULL COMMENT '开启状态',
    `memo`        varchar(2000)                                                          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_food_category_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='食材分类';

-- 菜谱菜单表
CREATE TABLE IF NOT EXISTS `meals_recipe_menu`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`     bigint                                                       DEFAULT NULL COMMENT '用户编号',
    `title`       varchar(255)                                                 DEFAULT NULL COMMENT '标题',
    `subtitle`    varchar(255)                                                 DEFAULT NULL COMMENT '副标题',
    `menu_desc`   text                                                         DEFAULT NULL COMMENT '菜单描述',
    `pic_url`     varchar(256)                                                 DEFAULT NULL COMMENT '菜单封面图',
    `memo`        varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `menu_type`   tinyint                                                      DEFAULT NULL COMMENT '菜单类型',
    `menu_status` tinyint  NOT NULL COMMENT '菜单状态',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_recipe_menu_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜谱菜单表';

-- 菜单菜谱表
CREATE TABLE IF NOT EXISTS `meals_menu_recipe`
(
    `id`             bigint   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `recipe_menu_id` bigint                                                       DEFAULT NULL COMMENT '菜谱菜单编号',
    `recipe_id` bigint                                                       DEFAULT NULL COMMENT '菜谱编号',
    `memo`           varchar(2000)                                                DEFAULT NULL COMMENT '备注',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`    datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`    datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_menu_recipe_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜单菜谱表';

-- 用户收藏夹表
CREATE TABLE IF NOT EXISTS `meals_user_collect`
(
    `id`             bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`        bigint                                                       NOT NULL DEFAULT 0 COMMENT '用户编号',
    `content_type`   tinyint                                                      NOT NULL DEFAULT 0 COMMENT '内容类型',
    `collect_name`   varchar(256)                                                 NOT NULL DEFAULT '' COMMENT '收藏夹名称',
    `pic_url`        varchar(256)                                                 NOT NULL DEFAULT '' COMMENT '封面图',
    `collect_desc`   varchar(2000)                                                NOT NULL DEFAULT '' COMMENT '简介',
    `collect_status` tinyint                                                      NOT NULL DEFAULT 0 COMMENT '收藏夹状态',
    `default_flag`   bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否默认',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_user_collect_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户收藏夹表';

-- 用户收藏表
CREATE TABLE IF NOT EXISTS `meals_user_favor`
(
    `id`           bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`      bigint                                                       NOT NULL DEFAULT 0 COMMENT '用户编号',
    `content_id`   bigint                                                       NOT NULL DEFAULT 0 COMMENT '内容编号',
    `content_type` tinyint                                                      NOT NULL DEFAULT 0 COMMENT '内容类型',
    `collect_id`   bigint                                                       NOT NULL DEFAULT 0 COMMENT '收藏夹ID',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    CONSTRAINT `pk_meals_user_favor_id` PRIMARY KEY (`id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户收藏表';

-- 用户评论表
CREATE TABLE IF NOT EXISTS `meals_user_comment`
(
    `id`                  bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '评论编号，主键自增',
    `user_id`             bigint                                                         NOT NULL COMMENT '评论人编号',
    `user_nickname`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '评价人名称',
    `user_avatar`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '评价人头像',
    `content_id`          bigint                                                         NOT NULL DEFAULT 0 COMMENT '内容编号',
    `content_type`        tinyint                                                        NOT NULL DEFAULT 0 COMMENT '内容类型',
    `content_user_id`             bigint                                                         NOT NULL DEFAULT 0 COMMENT '内容所属人编号',
    `comment_content`     varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
    `comment_author`      bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否内容作者',
    `user_read`      bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否已读',
    `pic_urls`            varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '评论图片地址数组',
    `reply_user_id`       bigint                                                         NULL     DEFAULT NULL COMMENT '回复的用户编号',
    `reply_user_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '回复的用户名称',
    `reply_content`       varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '回复的内容',
    `creator`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint                                                         NOT NULL DEFAULT 0 COMMENT '租户编号',
    CONSTRAINT `pk_meals_user_comment_id` PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '评论表';


-- 用户会话表
CREATE TABLE IF NOT EXISTS `meals_user_chat_conversation`
(
    `id`                        bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
    `user_id`                   bigint                                                         NOT NULL COMMENT '会话所属用户',
    `chat_user_id`              bigint                                                         NOT NULL COMMENT '会话聊天对象用户',
    `last_message_time`         datetime                                                       NOT NULL COMMENT '最后聊天时间',
    `last_message_content`      varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最后聊天内容',
    `last_message_content_type` int                                                            NOT NULL COMMENT '最后发送的消息类型',
    `pinned`                    bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否置顶',
    `user_deleted`              bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '用户是否可见（用户删除对话列表）',
    `unread_message_count`      int                                                            NOT NULL COMMENT '未读消息数',
    `creator`                   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time`               datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '更新者',
    `update_time`               datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`                   bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`                 bigint                                                         NOT NULL DEFAULT 0 COMMENT '租户编号',
    CONSTRAINT `pk_meals_user_chat_conversation_id` PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户会话表';

-- 用户消息表
CREATE TABLE IF NOT EXISTS `meals_user_chat_message`
(
    `id`               bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
    `conversation_id`  bigint                                                         NOT NULL COMMENT '会话编号',
    `sender_user_id`   bigint                                                         NOT NULL COMMENT '发送人编号',
    `receiver_user_id` bigint                                                                  DEFAULT NOT NULL COMMENT '接收人编号',
    `content_type`     int                                                            NOT NULL COMMENT '消息类型',
    `content`          varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息',
    `read_status`      bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否已读',
    `creator`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time`      datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '更新者',
    `update_time`      datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`        bigint                                                         NOT NULL DEFAULT 0 COMMENT '租户编号',
    CONSTRAINT `pk_meals_user_chat_message_id` PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户消息表';
