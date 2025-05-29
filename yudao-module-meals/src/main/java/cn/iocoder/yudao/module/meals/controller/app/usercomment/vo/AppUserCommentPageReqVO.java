package cn.iocoder.yudao.module.meals.controller.app.usercomment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评论分页 Request VO")
@Data
public class AppUserCommentPageReqVO extends PageParam {

    @Schema(description = "评论人编号", example = "10081")
    private Long userId;

    @Schema(description = "评价人名称", example = "芋艿")
    private String userNickname;

    @Schema(description = "评价人头像")
    private String userAvatar;

    @Schema(description = "内容编号", example = "10501")
    private Long contentId;

    @Schema(description = "内容类型", example = "1")
    private Integer contentType;

    @Schema(description = "评论内容")
    private String commentContent;

    @Schema(description = "是否内容作者")
    private Boolean commentAuthor;

    @Schema(description = "评论图片地址数组")
    private String[] picUrls;

    @Schema(description = "回复的用户编号", example = "27670")
    private Long replyUserId;

    @Schema(description = "回复的用户名称", example = "芋艿")
    private String replyUserNickname;

    @Schema(description = "回复的内容")
    private String replyContent;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
