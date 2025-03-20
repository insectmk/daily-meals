package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;

@Schema(description = "用户 APP - 菜谱新增/修改 Request VO")
@Data
public class AppRecipeSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23107")
    private Long id;

    @Schema(description = "用户编号", example = "21961")
    private Long userId;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "简介")
    private String recipeDesc;

    @Schema(description = "教程")
    private String recipeStep;

    @Schema(description = "菜谱类型", example = "2")
    private Integer recipeType;

    @Schema(description = "烹饪难度")
    private Integer recipeLevel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

}