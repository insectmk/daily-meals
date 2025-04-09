package cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo;

import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 每日计划明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppDailyPlanItemDetailRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15244")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "计划ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29970")
    @ExcelProperty("计划ID")
    private Long planId;

    @Schema(description = "菜谱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31143")
    @ExcelProperty("菜谱ID")
    private Long recipeId;

    @Schema(description = "餐次类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("餐次类型")
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "菜谱名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("菜谱名称")
    private String recipeName;

    @Schema(description = "菜谱信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("菜谱信息")
    private AppRecipeRespVO recipeInfo;
}
