package cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户APP - 用户聊天消息 Response VO")
@Data
public class AppUserChatMessageRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23202")
    private Long id;

    @Schema(description = "会话编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12580")
    private Long conversationId;

    @Schema(description = "发送人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24571")
    private Long senderUserId;
    @Schema(description = "发送人头像", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://yudao.com/images/avatar.jpg")
    private String senderUserAvatar;

    @Schema(description = "接收人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29124")
    private Long receiverUserId;

    @Schema(description = "消息类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer contentType;

    @Schema(description = "消息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "是否已读", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Boolean readStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
