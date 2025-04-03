package cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "用户 APP - 每日计划明细新增/修改 Request VO")
@Data
public class AppDailyPlanItemSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15244")
    private Long id;

    @Schema(description = "计划ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29970")
    @NotNull(message = "计划ID不能为空")
    private Long planId;

    @Schema(description = "菜谱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31143")
    @NotNull(message = "菜谱ID不能为空")
    private Long recipeId;

    @Schema(description = "餐次类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "餐次类型不能为空")
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

}