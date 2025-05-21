package cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 每日计划明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDailyPlanItemPageReqVO extends PageParam {

    @Schema(description = "计划ID", example = "29970")
    private Long planId;

    @Schema(description = "菜谱ID", example = "31143")
    private Long recipeId;

    @Schema(description = "餐次类型", example = "1")
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}