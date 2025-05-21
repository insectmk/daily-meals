package cn.iocoder.yudao.module.meals.controller.admin.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 菜谱精简信息 Response VO")
@Data
@ToString(callSuper = true)
public class RecipeSimpleRespVO {

    @Schema(description = "编号", example = "6379")
    private Long id;

    @Schema(description = "菜谱名称", example = "番茄炒鸡蛋")
    private String name;

}
