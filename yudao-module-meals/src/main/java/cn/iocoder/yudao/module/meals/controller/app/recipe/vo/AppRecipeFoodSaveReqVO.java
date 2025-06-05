package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 菜谱食材新增/修改 Request VO")
@Data
public class AppRecipeFoodSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23107")
    private Long id;
    @Schema(description = "菜谱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23107")
    private Long recipeId;
    @Schema(description = "量", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.05")
    private Double amount;
    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "这是一个 备注")
    private String memo;
    @Schema(description = "食材名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "西红柿")
    private String foodName;
    @Schema(description = "食材单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "g")
    private String foodUnit;

}
