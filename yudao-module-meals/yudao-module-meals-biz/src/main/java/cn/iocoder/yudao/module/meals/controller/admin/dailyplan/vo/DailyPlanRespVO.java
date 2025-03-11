package cn.iocoder.yudao.module.meals.controller.admin.dailyplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 菜谱计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DailyPlanRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22481")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "菜谱ID", example = "12195")
    @ExcelProperty("菜谱ID")
    private Long recipeId;

    @Schema(description = "计划日")
    @ExcelProperty("计划日")
    private LocalDateTime planDate;

    @Schema(description = "餐次类型", example = "1")
    @ExcelProperty(value = "餐次类型", converter = DictConvert.class)
    @DictFormat("meals_meal_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}