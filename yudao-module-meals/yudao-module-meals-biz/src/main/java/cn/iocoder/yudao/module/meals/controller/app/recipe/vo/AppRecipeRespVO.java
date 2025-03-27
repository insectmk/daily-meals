package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "用户 APP - 菜谱 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppRecipeRespVO {
    // ========== 食谱基本信息 ==========

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23107")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户编号", example = "21961")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "名称", example = "李四")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "简介")
    @ExcelProperty("简介")
    private String recipeDesc;

    @Schema(description = "教程")
    @ExcelProperty("教程")
    private String recipeStep;

    @Schema(description = "菜谱类型", example = "2")
    @ExcelProperty("菜谱类型")
    private Integer recipeType;

    @Schema(description = "烹饪难度")
    @ExcelProperty("烹饪难度")
    private Integer recipeLevel;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    // ========== 食谱食材信息 ==========
    List<AppRecipeFoodDetailRespVO> foods;

}
