package cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "用户 APP - 菜谱计划新增/修改 Request VO")
@Data
public class AppDailyPlanSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10060")
    private Long id;

    @Schema(description = "菜谱ID", example = "12195")
    private Long recipeId;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28829")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "计划日")
    private LocalDateTime planDate;

    @Schema(description = "餐次类型", example = "1")
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

}
