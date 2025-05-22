package cn.iocoder.yudao.module.meals.controller.app.usercollect.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 用户收藏夹分页 Request VO")
@Data
public class AppUserCollectPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "28613")
    private Long userId;

    @Schema(description = "内容类型", example = "2")
    private Integer contentType;

    @Schema(description = "收藏夹名称", example = "王五")
    private String collectName;

    @Schema(description = "封面图", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "简介")
    private String collectDesc;

    @Schema(description = "收藏夹状态", example = "1")
    private Integer collectStatus;

    @Schema(description = "是否默认收藏夹", example = "true")
    private Boolean defaultFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
