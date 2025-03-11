package cn.iocoder.yudao.module.meals.controller.admin.dailyplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 菜谱计划新增/修改 Request VO")
@Data
public class DailyPlanSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22481")
    private Long id;

    @Schema(description = "菜谱ID", example = "12195")
    private Long recipeId;

    @Schema(description = "计划日")
    private LocalDateTime planDate;

    @Schema(description = "餐次类型", example = "1")
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

}