package cn.iocoder.yudao.module.meals.websocket.message;

import lombok.Data;

/**
 * 示例：client -> server 发送消息
 *
 * @author InsectMk
 */
@Data
public class UserChatSendMessage {

    /**
     * 发送给谁
     *
     * 如果为空，说明发送给所有人
     */
    private Long toUserId;
    /**
     * 内容
     */
    private String text;

}
