package cn.iocoder.yudao.module.meals.controller.app.usercollect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 用户收藏夹 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppUserCollectRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1498")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28613")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "内容类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("内容类型")
    private Integer contentType;

    @Schema(description = "收藏夹名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("收藏夹名称")
    private String collectName;

    @Schema(description = "封面图", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("封面图")
    private String picUrl;

    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("简介")
    private String collectDesc;

    @Schema(description = "收藏夹状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("收藏夹状态")
    private Integer collectStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}