package cn.iocoder.yudao.module.meals.controller.app.usercomment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 评论新增/修改 Request VO")
@Data
public class AppUserCommentSaveReqVO {

    @Schema(description = "评论编号，主键自增", requiredMode = Schema.RequiredMode.REQUIRED, example = "3522")
    private Long id;

    @Schema(description = "评论人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10081")
    private Long userId;

    @Schema(description = "评价人名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    private String userNickname;

    @Schema(description = "评价人头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAvatar;

    @Schema(description = "内容编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10501")
    @NotNull(message = "内容编号不能为空")
    private Long contentId;

    @Schema(description = "内容类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;

    @Schema(description = "内容所属人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10081")
    private Long contentUserId;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "评论内容不能为空")
    private String commentContent;

    @Schema(description = "是否内容作者", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean commentAuthor;

    @Schema(description = "是否已读", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean userRead;

    @Schema(description = "评论图片地址数组")
    private String[] picUrls;

    @Schema(description = "回复的用户编号", example = "27670")
    private Long replyUserId;

    @Schema(description = "回复的用户名称", example = "芋艿")
    private String replyUserNickname;

    @Schema(description = "回复的内容")
    private String replyContent;

}
