package cn.iocoder.yudao.module.meals.controller.app.usercollect.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 用户收藏夹 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppUserCollectSimpleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1498")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "收藏夹名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("收藏夹名称")
    private String collectName;

    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("简介")
    private String collectDesc;

    @Schema(description = "收藏夹状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("收藏夹状态")
    private Integer collectStatus;

}
