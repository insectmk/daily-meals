package cn.iocoder.yudao.module.meals.controller.admin.food.vo;

import cn.iocoder.yudao.module.meals.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 食材 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FoodRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7107")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "分类", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.MEALS_FOOD_TYPE)
    private Integer foodType;

    @Schema(description = "食材分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "11, 22")
    @ExcelProperty("食材分类")
    private List<Long> foodCategory;

    @Schema(description = "单位", example = "1")
    @ExcelProperty(value = "单位", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.MEALS_FOOD_UNIT)
    private Integer foodUnit;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
