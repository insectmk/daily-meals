package cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem;

import lombok.*;

/**
 * 每日计划明细 DO
 *
 * @author InsectMk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlanItemDetailDO extends DailyPlanItemDO {
    /**
     * 菜谱名称
     */
    private String recipeName;
}
