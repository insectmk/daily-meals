package cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 菜谱加入到计划 Request VO")
@Data
public class AppDailyPlanRecipeSaveTodayReqVO {

    @Schema(description = "菜谱ID集合", example = "[1,2,3]")
    private List<Long> recipeIds;

    @Schema(description = "计划日")
    @NotNull(message = "计划日不能为空")
    private LocalDateTime planDate;

    @Schema(description = "餐次类型", example = "1")
    @NotNull(message = "餐次类型不能为空")
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

}
