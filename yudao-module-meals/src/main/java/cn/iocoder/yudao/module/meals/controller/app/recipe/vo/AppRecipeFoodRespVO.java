package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 菜谱食材详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppRecipeFoodRespVO {
    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23107")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "菜谱编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23107")
    @ExcelProperty("菜谱编号")
    private Long recipeId;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "2.2")
    @ExcelProperty("数量")
    private Double amount;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
    private String foodName;

    @Schema(description = "单位", example = "1")
    @ExcelProperty(value = "单位", converter = DictConvert.class)
    @DictFormat("meals_food_unit")
    private String foodUnit;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;
}
