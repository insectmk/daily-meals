package cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 每日计划明细 DO
 *
 * @author InsectMk
 */
@TableName("meals_daily_plan_item")
@KeySequence("meals_daily_plan_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlanItemDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 计划ID
     */
    private Long planId;
    /**
     * 菜谱ID
     */
    private Long recipeId;
    /**
     * 餐次类型
     */
    private Integer mealType;
    /**
     * 备注
     */
    private String memo;

}