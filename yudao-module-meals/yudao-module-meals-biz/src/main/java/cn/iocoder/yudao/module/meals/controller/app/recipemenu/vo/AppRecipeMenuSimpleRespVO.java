package cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 食材精简信息 Response VO")
@Data
@ToString(callSuper = true)
public class AppRecipeMenuSimpleRespVO {

    @Schema(description = "编号", example = "6379")
    private Long id;

    @Schema(description = "菜单标题", example = "夏日菜单，真的是太美味了！")
    private String title;

}
