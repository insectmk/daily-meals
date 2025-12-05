package cn.iocoder.yudao.module.meals.controller.app.userfavor.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 用户收藏 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppUserFavorRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5241")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24330")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "内容编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30819")
    @ExcelProperty("内容编号")
    private Long contentId;

    @Schema(description = "内容类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("内容类型")
    private Integer contentType;

    @Schema(description = "收藏夹ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26100")
    @ExcelProperty("收藏夹ID")
    private Long collectId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
