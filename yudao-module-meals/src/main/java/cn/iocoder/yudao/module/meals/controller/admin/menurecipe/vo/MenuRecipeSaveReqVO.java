package cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 菜单菜谱新增/修改 Request VO")
@Data
public class MenuRecipeSaveReqVO {
    @Schema(description = "菜单菜谱编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5503")
    private Long id;

    @Schema(description = "菜谱菜单编号", example = "30175")
    private Long recipeMenuId;

    @Schema(description = "备注", example = "你猜")
    private String memo;

}
