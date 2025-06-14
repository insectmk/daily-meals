package cn.iocoder.yudao.module.meals.service.userchat;


import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation.AppUserChatConversationUpdatePinnedReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatConversationDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;

import java.util.List;

/**
 * 客服会话 Service 接口
 *
 * @author HUIHUI
 */
public interface AppUserChatConversationService {

    /**
     * 获得客服会话
     *
     * @param id 编号
     * @return 客服会话
     */
    UserChatConversationDO getConversation(Long id);

    /**
     * 【管理员】删除客服会话
     *
     * @param id 编号
     */
    void deleteKefuConversation(Long id);

    /**
     * 【管理员】客服会话置顶
     *
     * @param updateReqVO 请求
     */
    void updateConversationPinnedByAdmin(AppUserChatConversationUpdatePinnedReqVO updateReqVO);

    /**
     * 更新会话消息冗余信息
     *
     * @param userChatMessage 消息
     */
    void updateConversationLastMessage(UserChatMessageDO userChatMessage);

    /**
     * 【管理员】将管理员未读消息计数更新为零
     *
     * @param id 编号
     */
    void updateAdminUnreadMessageCountToZero(Long id);

    /**
     * 【管理员】更新会话对于管理员是否可见
     *
     * @param id           编号
     * @param adminDeleted 管理员是否可见
     */
    void updateConversationAdminDeleted(Long id, Boolean adminDeleted);

    /**
     * 获得用户会话列表
     *
     * @param userId  会话所属用户ID
     * @return 会话列表
     */
    List<UserChatConversationDO> getUserChatConversationList(Long userId
    );

    /**
     * 用户获得或创建会话
     *
     * 对于用户来说，有且仅有一个对话
     *
     * @param senderUserId 发送人用户编号
     * @param receiverUserId 接收人的用户编号
     * @return 用户会话
     */
    UserChatConversationDO getOrCreateConversation(Long senderUserId, Long receiverUserId);

    /**
     * 校验客服会话是否存在
     *
     * @param id 编号
     * @return 客服会话
     */
    UserChatConversationDO validateKefuConversationExists(Long id);

    /**
     * 获得用户会话
     * @param senderUserId 发送人ID
     * @param receiverUserId 接收方ID
     * @return 用户会话
     */
    UserChatConversationDO getConversationByUserId(Long senderUserId, Long receiverUserId);

}
