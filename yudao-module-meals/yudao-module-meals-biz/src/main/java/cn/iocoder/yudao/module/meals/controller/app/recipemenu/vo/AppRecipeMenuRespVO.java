package cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo;

import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 菜谱菜单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppRecipeMenuRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14296")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户编号", example = "27498")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "副标题")
    @ExcelProperty("副标题")
    private String subtitle;

    @Schema(description = "菜单描述")
    @ExcelProperty("菜单描述")
    private String menuDesc;

    @Schema(description = "菜单封面图", example = "https://www.iocoder.cn")
    @ExcelProperty("菜单封面图")
    private String picUrl;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    // *********详细信息**********

    @Schema(description = "菜谱信息列表")
    private List<AppRecipeRespVO> recipes;
}
