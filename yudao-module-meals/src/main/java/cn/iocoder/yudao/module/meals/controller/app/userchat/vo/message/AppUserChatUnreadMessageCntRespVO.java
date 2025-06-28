package cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户APP - 用户未读聊天消息 Response VO")
@Data
public class AppUserChatUnreadMessageCntRespVO {
    @Schema(description = "会话编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12580")
    private Long conversationId;

    @Schema(description = "未读消息数", requiredMode = Schema.RequiredMode.REQUIRED, example = "292")
    private Integer unreadCount;
}
