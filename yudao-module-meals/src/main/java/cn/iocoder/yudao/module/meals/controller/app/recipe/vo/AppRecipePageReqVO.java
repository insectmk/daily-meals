package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 菜谱分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppRecipePageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "21961")
    private Long userId;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "简介")
    private String recipeDesc;

    @Schema(description = "教程")
    private String recipeStep;

    @Schema(description = "菜谱类型", example = "2")
    private Integer recipeType;

    @Schema(description = "菜谱分类", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "22, 11")
    private List<Long> recipeCategory;

    @Schema(description = "食材分类", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "22, 11")
    private List<Long> foodCategory;

    @Schema(description = "烹饪难度")
    private Integer recipeLevel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    // ***********附加查询条件

    @Schema(description = "收藏夹编号", example = "1")
    private Long collectId;

    @Schema(description = "食材名称", example = "['番茄','鸡蛋']")
    private List<String> foodNames;

    @Schema(description = "关注用户的菜谱", example = "true")
    private Boolean userFavor;
}
