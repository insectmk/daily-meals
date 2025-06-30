package cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户APP - 用户聊天消息已读请求 Request VO")
@Data
public class AppUserChatMessageReadByReceiverVO {
    @Schema(description = "接收方ID", example = "12580")
    @NotNull(message = "接收方ID不能为空")
    private Long receiverUserId;
}
