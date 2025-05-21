package cn.iocoder.yudao.module.meals.controller.app.userfavor.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 用户收藏分页 Request VO")
@Data
public class AppUserFavorPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "24330")
    private Long userId;

    @Schema(description = "内容编号", example = "30819")
    private Long contentId;

    @Schema(description = "内容类型", example = "1")
    private Integer contentType;

    @Schema(description = "收藏夹ID", example = "26100")
    private Long collectId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}