package cn.iocoder.yudao.module.meals.controller.admin.recipe.vo;

import cn.iocoder.yudao.module.meals.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 菜谱 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RecipeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "870")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("简介")
    private String recipeDesc;

    @Schema(description = "教程", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("教程")
    private String recipeStep;

    @Schema(description = "菜谱分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "11, 22")
    @ExcelProperty("菜谱分类")
    private List<Long> recipeCategory;

    @Schema(description = "烹饪难度", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "烹饪难度", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.MEALS_RECIPE_LEVEL)
    private Integer recipeLevel;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "菜谱封面图", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/xx.png")
    @ExcelProperty("菜谱封面图")
    private String picUrl;

    @Schema(description = "菜谱轮播图", requiredMode = Schema.RequiredMode.REQUIRED, example = "[https://www.iocoder.cn/xx.png, https://www.iocoder.cn/xxx.png]")
    private List<String> sliderPicUrls;

}
