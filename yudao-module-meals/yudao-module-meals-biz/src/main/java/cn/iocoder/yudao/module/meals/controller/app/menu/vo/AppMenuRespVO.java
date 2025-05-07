package cn.iocoder.yudao.module.meals.controller.app.menu.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 菜单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppMenuRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2858")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户编号", example = "16313")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "副标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("副标题")
    private String subtitle;

    @Schema(description = "菜单描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("菜单描述")
    private String menuDesc;

    @Schema(description = "菜单类型", example = "2")
    @ExcelProperty("菜单类型")
    private Integer menuType;

    @Schema(description = "菜单封面图", example = "https://www.iocoder.cn")
    @ExcelProperty("菜单封面图")
    private String picUrl;

    @Schema(description = "菜单菜谱ID集合", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("菜单菜谱ID集合")
    private String recipeIds;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "菜单状态", example = "2")
    @ExcelProperty("菜单状态")
    private Integer menuStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
