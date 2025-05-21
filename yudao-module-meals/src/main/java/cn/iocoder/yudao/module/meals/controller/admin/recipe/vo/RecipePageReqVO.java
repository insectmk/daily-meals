package cn.iocoder.yudao.module.meals.controller.admin.recipe.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 菜谱分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RecipePageReqVO extends PageParam {

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "菜谱类型", example = "1")
    private Integer recipeType;

    @Schema(description = "烹饪难度", example = "1")
    private Integer recipeLevel;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "菜谱分类", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "[22, 11]")
    private List<Long> recipeCategory;


    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
