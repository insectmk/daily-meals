package cn.iocoder.yudao.module.meals.dal.dataobject.menu;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 菜单 DO
 *
 * @author 珍珍
 */
@TableName("meals_menu")
@KeySequence("meals_menu_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDO extends BaseDO {

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
     * 标题
     */
    private String title;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 菜单描述
     */
    private String menuDesc;
    /**
     * 菜单类型
     */
    private Integer menuType;
    /**
     * 菜单封面图
     */
    private String picUrl;
    /**
     * 菜单菜谱ID集合
     */
    private String recipeIds;
    /**
     * 备注
     */
    private String memo;
    /**
     * 菜单状态
     */
    private Integer menuStatus;

}