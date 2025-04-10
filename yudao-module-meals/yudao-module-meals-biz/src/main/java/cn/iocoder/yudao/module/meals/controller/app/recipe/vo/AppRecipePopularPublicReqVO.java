package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Title: AppRecipePopularPublicReqVO
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.controller.app.recipe.vo
 * @Date 2025/4/9 17:15
 * @description: 最热门菜谱请求
 */
@Schema(description = "用户 APP - 最热门菜谱请求 Request VO")
@Data
public class AppRecipePopularPublicReqVO {
    @Schema(description = "排名数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "排名数量不能为空")
    private Integer rankLen;

    @Schema(description = "餐次类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "餐次类型不能为空")
    private Integer mealType;
}
