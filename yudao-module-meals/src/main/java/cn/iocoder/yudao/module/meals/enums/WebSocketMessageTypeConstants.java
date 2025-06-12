package cn.iocoder.yudao.module.meals.enums;

/**
 * DailyMeals 的 WebSocket 消息类型枚举类
 *
 * @author InsectMk
 */
public interface WebSocketMessageTypeConstants {

    // ======================= 每日饭菜 用户对话 =======================

    String USER_CHAT_MESSAGE_TYPE = "user_chat_message_type"; // 用户消息类型
    String USER_CHAT_MESSAGE_READ_STATUS_CHANGE = "user_chat_message_read_status_change"; // 用户消息已读

}
