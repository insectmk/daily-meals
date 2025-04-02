package cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "用户 APP - 菜谱加入到计划 Request VO")
@Data
public class AppDailyPlanRecipeSaveTodayReqVO {

    @Schema(description = "菜谱ID集合", example = "[1,2,3]")
    private List<Long> recipeIds;

    @Schema(description = "餐次类型", example = "1")
    private Integer mealType;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

}
