package cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 菜谱计划 DO
 *
 * @author InsectMk
 */
@TableName("meals_daily_plan")
@KeySequence("meals_daily_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlanDO extends BaseDO {

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
     * 计划日
     */
    private LocalDateTime planDate;
    /**
     * 餐次类型
     *
     * 枚举 {@link TODO meals_meal_type 对应的类}
     */
    private Integer mealType;
    /**
     * 备注
     */
    private String memo;

}