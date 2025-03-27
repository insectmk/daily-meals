package cn.iocoder.yudao.module.meals.dal.dataobject.recipe;

import lombok.*;

/**
 * @Title: RecipeDetailDO
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.dal.dataobject.recipe
 * @Date 2025/3/21 17:19
 * @description: 菜谱食材详情DO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFoodDetailDO extends RecipeFoodDO {
    /**
     * 食材名称
     */
    private String foodName;
    /**
     * 食材单位
     */
    private Long foodUnit;
}
