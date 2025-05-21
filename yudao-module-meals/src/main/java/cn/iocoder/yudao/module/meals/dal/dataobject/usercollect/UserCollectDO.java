package cn.iocoder.yudao.module.meals.dal.dataobject.usercollect;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户收藏夹 DO
 *
 * @author InsectMk
 */
@TableName("meals_user_collect")
@KeySequence("meals_user_collect_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCollectDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 内容类型
     */
    private Integer contentType;
    /**
     * 收藏夹名称
     */
    private String collectName;
    /**
     * 封面图
     */
    private String picUrl;
    /**
     * 简介
     */
    private String collectDesc;
    /**
     * 收藏夹状态
     */
    private Integer collectStatus;


}