package cn.iocoder.yudao.module.meals.dal.dataobject.userfavor;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户收藏 DO
 *
 * @author InsectMk
 */
@TableName("meals_user_favor")
@KeySequence("meals_user_favor_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFavorDO extends BaseDO {

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
     * 内容编号
     */
    private Long contentId;
    /**
     * 内容类型
     */
    private Integer contentType;
    /**
     * 收藏夹ID
     */
    private Long collectId;


}