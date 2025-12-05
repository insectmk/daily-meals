package cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 菜单菜谱 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MenuRecipeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16878")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "菜谱菜单编号", example = "30175")
    @ExcelProperty("菜谱菜单编号")
    private Long recipeMenuId;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
