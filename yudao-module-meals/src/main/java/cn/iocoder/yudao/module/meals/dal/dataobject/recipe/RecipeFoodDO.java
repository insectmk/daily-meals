package cn.iocoder.yudao.module.meals.dal.dataobject.recipe;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.module.meals.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 菜谱食材 DO
 *
 * @author InsectMk
 */
@TableName("meals_recipe_food")
@KeySequence("meals_recipe_food_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFoodDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 菜谱ID
     */
    private Long recipeId;
    /**
     * 食材ID
     */
    private Long foodId;
    /**
     * 量
     */
    private Double amount;
    /**
     * 备注
     */
    private String memo;
    /**
     * 食材名称
     */
    private String foodName;
    /**
     * 食材单位
     */
    private String foodUnit;
}
