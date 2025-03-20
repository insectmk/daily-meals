package cn.iocoder.yudao.module.meals.dal.dataobject.recipe;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 菜谱 DO
 *
 * @author InsectMk
 */
@TableName("meals_recipe")
@KeySequence("meals_recipe_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 名称
     */
    private String name;
    /**
     * 简介
     */
    private String recipeDesc;
    /**
     * 教程
     */
    private String recipeStep;
    /**
     * 菜谱类型
     *
     * 枚举 {@link TODO meals_recipe_type 对应的类}
     */
    private Integer recipeType;
    /**
     * 烹饪难度
     *
     * 枚举 {@link TODO meals_recipe_level 对应的类}
     */
    private Integer recipeLevel;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Integer status;

}
