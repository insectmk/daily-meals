package cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;

@Schema(description = "管理后台 - 菜谱菜单新增/修改 Request VO")
@Data
public class RecipeMenuSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14296")
    private Long id;

    @Schema(description = "用户编号", example = "27498")
    private Long userId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "副标题")
    private String subtitle;

    @Schema(description = "菜单描述")
    private String menuDesc;

    @Schema(description = "菜单封面图", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "菜单类型")
    private Integer menuType;

    @Schema(description = "菜单状态")
    private Integer menuStatus;

    @Schema(description = "备注", example = "你猜")
    private String memo;

}
