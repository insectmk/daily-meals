package cn.iocoder.yudao.module.meals.websocket;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.websocket.core.listener.WebSocketMessageListener;
import cn.iocoder.yudao.framework.websocket.core.sender.WebSocketMessageSender;
import cn.iocoder.yudao.framework.websocket.core.util.WebSocketFrameworkUtils;
import cn.iocoder.yudao.module.meals.websocket.message.UserChatReceiveMessage;
import cn.iocoder.yudao.module.meals.websocket.message.UserChatSendMessage;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket ：用户聊天，不存储，纯websocket
 *
 * @author InsectMk
 */
@Component
public class UserChatWebSocketMessageListener implements WebSocketMessageListener<UserChatSendMessage> {

    @Resource
    private WebSocketMessageSender webSocketMessageSender;

    @Override
    public void onMessage(WebSocketSession session, UserChatSendMessage message) {
        Long fromUserId = WebSocketFrameworkUtils.getLoginUserId(session);
        // 情况一：单发
        if (message.getToUserId() != null) {
            UserChatReceiveMessage toMessage = new UserChatReceiveMessage().setFromUserId(fromUserId)
                    .setText(message.getText()).setSingle(true);
            webSocketMessageSender.sendObject(UserTypeEnum.MEMBER.getValue(), message.getToUserId(), // 给指定用户
                    "user-chat-message-receive", toMessage);
            return;
        }
        // 情况二：群发
        UserChatReceiveMessage toMessage = new UserChatReceiveMessage().setFromUserId(fromUserId)
                .setText(message.getText()).setSingle(false);
        webSocketMessageSender.sendObject(UserTypeEnum.ADMIN.getValue(), // 给所有用户
                "user-chat-message-receive", toMessage);
    }

    @Override
    public String getType() {
        return "user-chat-message-send";
    }

}
