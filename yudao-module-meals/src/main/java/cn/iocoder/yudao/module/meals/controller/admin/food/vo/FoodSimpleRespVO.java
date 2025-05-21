package cn.iocoder.yudao.module.meals.controller.admin.food.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 食材精简信息 Response VO")
@Data
@ToString(callSuper = true)
public class FoodSimpleRespVO {

    @Schema(description = "编号", example = "6379")
    private Long id;

    @Schema(description = "食材名称", example = "番茄")
    private String name;

}
