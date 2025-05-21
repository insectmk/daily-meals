package cn.iocoder.yudao.module.meals.dal.dataobject.food;

import cn.iocoder.yudao.framework.mybatis.core.type.LongListTypeHandler;
import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 食材 DO
 *
 * @author InsectMk
 */
@TableName(value = "meals_food", autoResultMap = true)
@KeySequence("meals_food_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     *
     * 枚举 {@link TODO meals_food_type 对应的类}
     */
    private Integer foodType;
    /**
     * 食材分类
     *
     * 食材分类，多个,分割
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> foodCategory;
    /**
     * 单位
     *
     * 枚举 {@link TODO meals_food_unit 对应的类}
     */
    private Integer foodUnit;
    /**
     * 备注
     */
    private String memo;

}
