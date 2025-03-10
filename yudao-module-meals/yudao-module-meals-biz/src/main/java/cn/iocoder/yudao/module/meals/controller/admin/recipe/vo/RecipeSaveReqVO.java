package cn.iocoder.yudao.module.meals.controller.admin.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;

@Schema(description = "管理后台 - 菜谱新增/修改 Request VO")
@Data
public class RecipeSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "简介不能为空")
    private String recipeDesc;

    @Schema(description = "教程", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "教程不能为空")
    private String recipeStep;

    @Schema(description = "菜谱类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "菜谱类型不能为空")
    private Integer recipeType;

    @Schema(description = "标签", example = "1")
    private Integer recipeTag;

    @Schema(description = "烹饪难度", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "烹饪难度不能为空")
    private Integer recipeLevel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

}