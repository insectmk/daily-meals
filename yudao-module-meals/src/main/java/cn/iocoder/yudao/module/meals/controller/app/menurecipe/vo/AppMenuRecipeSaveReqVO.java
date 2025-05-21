package cn.iocoder.yudao.module.meals.controller.app.menurecipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 菜单菜谱新增/修改 Request VO")
@Data
public class AppMenuRecipeSaveReqVO {
    @Schema(description = "菜单菜谱编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5503")
    private Long id;

    @Schema(description = "菜谱菜单编号", example = "30175")
    @NotNull(message = "菜谱菜单编号不能为空")
    private Long recipeMenuId;

    @Schema(description = "菜谱编号", example = "30175")
    @NotNull(message = "菜谱编号不能为空")
    private Long recipeId;

    @Schema(description = "备注", example = "你猜")
    private String memo;

}
