package cn.iocoder.yudao.module.meals.controller.app.food.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 食材 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppFoodRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7107")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "分类", converter = DictConvert.class)
    @DictFormat("meals_food_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer foodType;

    @Schema(description = "单位", example = "1")
    @ExcelProperty(value = "单位", converter = DictConvert.class)
    @DictFormat("meals_food_unit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer foodUnit;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
