package cn.iocoder.yudao.module.meals.controller.admin.recipe.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.module.meals.enums.DictTypeConstants;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 菜谱食材 ResponseFood VO")
@Data
@ExcelIgnoreUnannotated
public class RecipeFoodRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "菜谱编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("菜谱编号")
    private Long recipeId;

    @Schema(description = "食材编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("食材编号")
    private Long foodId;

    @Schema(description = "量", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("量")
    private Double amount;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "食材名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("食材名称")
    private String foodName;

    @Schema(description = "食材单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("食材单位")
    @DictFormat(DictTypeConstants.MEALS_FOOD_UNIT)
    private String foodUnit;

}
