package cn.iocoder.yudao.module.meals.service.userchat;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation.AppUserChatConversationMarkReadStatusReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation.AppUserChatConversationUpdatePinnedReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatConversationDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;
import cn.iocoder.yudao.module.meals.dal.mysql.userchat.UserChatConversationMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.userchat.UserChatMessageMapper;
import cn.iocoder.yudao.module.meals.enums.userchat.UserChatMessageContentTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.USER_CHAT_CONVERSATION_NOT_EXISTS;


/**
 * 客服会话 Service 实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class AppUserChatConversationServiceImpl implements AppUserChatConversationService {

    @Resource
    private UserChatConversationMapper conversationMapper;
    @Resource
    private UserChatMessageMapper messageMapper;
    @Resource
    @Lazy
    private AppUserChatMessageService messageService;

    @Override
    public UserChatConversationDO getConversation(Long id) {
        return conversationMapper.selectById(id);
    }

    @Override
    public void deleteKefuConversation(Long id) {
        // 校验存在
        validateConversationExists(id);

        // 只有管理员端可以删除会话，也不真的删，只是管理员端看不到啦
        conversationMapper.updateById(new UserChatConversationDO().setId(id).setUserDeleted(Boolean.TRUE));
    }

    @Override
    public void updateConversationPinned(AppUserChatConversationUpdatePinnedReqVO updateReqVO) {
        // 校验存在
        validateConversationExists(updateReqVO.getId());
        // 更新会话置顶状态
        conversationMapper.updateById(new UserChatConversationDO()
                .setId(updateReqVO.getId())
                .setPinned(updateReqVO.getPinned()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConversationLastMessage(UserChatMessageDO userChatMessage) {
        // 1.1 校验会话是否存在
        UserChatConversationDO conversation = validateConversationExists(userChatMessage.getConversationId());
        // 1.2 更新会话消息冗余
        conversationMapper.updateById(new UserChatConversationDO().setId(userChatMessage.getConversationId())
                .setLastMessageTime(userChatMessage.getCreateTime()).setLastMessageContent(userChatMessage.getContent())
                .setLastMessageContentType(userChatMessage.getContentType()));
        // 1.2 用户发送消息时，如果用户删除过会话则进行恢复
        if (Boolean.TRUE.equals(conversation.getUserDeleted())) {
            updateConversationAdminDeleted(userChatMessage.getConversationId(), Boolean.FALSE);
        }
    }

    @Override
    public void updateAdminUnreadMessageCountToZero(Long id) {
        // 校验存在
        validateConversationExists(id);

        // 管理员未读消息数归零
        conversationMapper.updateById(new UserChatConversationDO().setId(id).setUnreadMessageCount(0));
    }

    @Override
    public void updateConversationAdminDeleted(Long id, Boolean adminDeleted) {
        conversationMapper.updateById(new UserChatConversationDO().setId(id).setUserDeleted(adminDeleted));
    }

    @Override
    public List<UserChatConversationDO> getUserChatConversationList(Long userId) {
        return conversationMapper.selectConversationList(userId);
    }

    @Override
    public UserChatConversationDO getOrCreateConversation(Long senderUserId, Long receiverUserId) {
        // 查询对话
        UserChatConversationDO conversation = conversationMapper.selectOne(
                UserChatConversationDO::getUserId, senderUserId, // 发送人
                UserChatConversationDO::getChatUserId, receiverUserId); // 接收人
        // 没有历史会话，则初始化一个新会话
        if (conversation == null) {
            conversation = new UserChatConversationDO()
                    .setUserId(senderUserId) // 所属用户
                    .setChatUserId(receiverUserId) // 聊天对象用户
                    .setLastMessageTime(LocalDateTime.now()) // 最后发送消息时间
                    .setLastMessageContent(StrUtil.EMPTY) // 最后发送内容
                    .setLastMessageContentType(UserChatMessageContentTypeEnum.TEXT.getType()) // 最后发送内容类型
                    .setPinned(Boolean.FALSE) // 是否置顶
                    .setUserDeleted(Boolean.FALSE) // 用户是否删除
                    .setUnreadMessageCount(0); // 未读消息数
            conversationMapper.insert(conversation);
        }
        return conversation;
    }

    @Override
    public UserChatConversationDO validateConversationExists(Long id) {
        UserChatConversationDO conversation = conversationMapper.selectById(id);
        if (conversation == null) {
            throw exception(USER_CHAT_CONVERSATION_NOT_EXISTS);
        }
        return conversation;
    }

    @Override
    public UserChatConversationDO getConversationByUserId(Long senderUserId, Long receiverUserId) {
        return conversationMapper.selectByUserId(senderUserId, receiverUserId);
    }

    @Override
    public void markReadStatus(AppUserChatConversationMarkReadStatusReqVO markReqVO) {
        // 校验存在
        UserChatConversationDO conversationDO = validateConversationExists(markReqVO.getId());
        if (markReqVO.getReadStatus()) {
            // 已读
            messageService.conversationRead(markReqVO.getId(), conversationDO.getUserId());
        } else {
            // 未读
            // 更新会话发送方最新聊天为未读
            UserChatMessageDO messageDO = messageMapper.selectFirstOne(
                    UserChatMessageDO::getConversationId, conversationDO.getId(),
                    UserChatMessageDO::getReceiverUserId, conversationDO.getUserId()
            );
            messageMapper.updateById(new UserChatMessageDO()
                    .setId(messageDO.getId())
                    .setReadStatus(Boolean.FALSE));
        }
    }

}
