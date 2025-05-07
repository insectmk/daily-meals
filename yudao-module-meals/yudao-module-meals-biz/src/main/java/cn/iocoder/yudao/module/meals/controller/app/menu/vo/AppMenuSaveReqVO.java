package cn.iocoder.yudao.module.meals.controller.app.menu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(description = "管理后台 - 菜单新增/修改 Request VO")
@Data
public class AppMenuSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2858")
    private Long id;

    @Schema(description = "用户编号", example = "16313")
    private Long userId;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "标题不能为空")
    private String title;

    @Schema(description = "副标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "副标题不能为空")
    private String subtitle;

    @Schema(description = "菜单描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "菜单描述不能为空")
    private String menuDesc;

    @Schema(description = "菜单类型", example = "2")
    private Integer menuType;

    @Schema(description = "菜单封面图", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "菜单菜谱ID集合", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "菜单菜谱ID集合不能为空")
    private String recipeIds;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

    @Schema(description = "菜单状态", example = "2")
    private Integer menuStatus;

}
