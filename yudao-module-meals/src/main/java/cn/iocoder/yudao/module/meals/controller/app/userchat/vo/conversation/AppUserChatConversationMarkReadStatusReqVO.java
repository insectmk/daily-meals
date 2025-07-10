package cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户APP - 标记未读/已读状态 Request VO")
@Data
public class AppUserChatConversationMarkReadStatusReqVO {
    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23202")
    @NotNull(message = "会话编号不能为空")
    private Long id;

    @Schema(description = "阅读状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull(message = "阅读状态不能为空")
    private Boolean readStatus;
}
