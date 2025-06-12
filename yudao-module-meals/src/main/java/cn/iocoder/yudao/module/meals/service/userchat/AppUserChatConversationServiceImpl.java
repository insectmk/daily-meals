package cn.iocoder.yudao.module.meals.service.userchat;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation.AppUserChatConversationUpdatePinnedReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatConversationDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;
import cn.iocoder.yudao.module.meals.dal.mysql.userchat.UserChatConversationMapper;
import cn.iocoder.yudao.module.meals.enums.userchat.UserChatMessageContentTypeEnum;
import jakarta.annotation.Resource;
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

    @Override
    public UserChatConversationDO getConversation(Long id) {
        return conversationMapper.selectById(id);
    }

    @Override
    public void deleteKefuConversation(Long id) {
        // 校验存在
        validateKefuConversationExists(id);

        // 只有管理员端可以删除会话，也不真的删，只是管理员端看不到啦
        conversationMapper.updateById(new UserChatConversationDO().setId(id).setUserDeleted(Boolean.TRUE));
    }

    @Override
    public void updateConversationPinnedByAdmin(AppUserChatConversationUpdatePinnedReqVO updateReqVO) {
        // 校验存在
        validateKefuConversationExists(updateReqVO.getId());

        // 更新管理员会话置顶状态
        conversationMapper.updateById(new UserChatConversationDO().setId(updateReqVO.getId()).setPinned(updateReqVO.getPinned()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConversationLastMessage(UserChatMessageDO kefuMessage) {
        // 1.1 校验会话是否存在
        UserChatConversationDO conversation = validateKefuConversationExists(kefuMessage.getConversationId());
        // 1.2 更新会话消息冗余
        conversationMapper.updateById(new UserChatConversationDO().setId(kefuMessage.getConversationId())
                .setLastMessageTime(kefuMessage.getCreateTime()).setLastMessageContent(kefuMessage.getContent())
                .setLastMessageContentType(kefuMessage.getContentType()));

        // 2.1 更新管理员未读消息数
        conversationMapper.updateAdminUnreadMessageCountIncrement(kefuMessage.getConversationId());
        // 2.2 会员用户发送消息时，如果管理员删除过会话则进行恢复
        if (Boolean.TRUE.equals(conversation.getUserDeleted())) {
            updateConversationAdminDeleted(kefuMessage.getConversationId(), Boolean.FALSE);
        }
    }

    @Override
    public void updateAdminUnreadMessageCountToZero(Long id) {
        // 校验存在
        validateKefuConversationExists(id);

        // 管理员未读消息数归零
        conversationMapper.updateById(new UserChatConversationDO().setId(id).setUnreadMessageCount(0));
    }

    @Override
    public void updateConversationAdminDeleted(Long id, Boolean adminDeleted) {
        conversationMapper.updateById(new UserChatConversationDO().setId(id).setUserDeleted(adminDeleted));
    }

    @Override
    public List<UserChatConversationDO> getKefuConversationList() {
        return conversationMapper.selectConversationList();
    }

    @Override
    public UserChatConversationDO getOrCreateConversation(Long userId) {
        UserChatConversationDO conversation = conversationMapper.selectOne(UserChatConversationDO::getUserId, userId);
        // 没有历史会话，则初始化一个新会话
        if (conversation == null) {
            conversation = new UserChatConversationDO().setUserId(userId).setLastMessageTime(LocalDateTime.now())
                    .setLastMessageContent(StrUtil.EMPTY).setLastMessageContentType(UserChatMessageContentTypeEnum.TEXT.getType())
                    .setPinned(Boolean.FALSE).setUserDeleted(Boolean.FALSE).setUserDeleted(Boolean.FALSE)
                    .setUnreadMessageCount(0);
            conversationMapper.insert(conversation);
        }
        return conversation;
    }

    @Override
    public UserChatConversationDO validateKefuConversationExists(Long id) {
        UserChatConversationDO conversation = conversationMapper.selectById(id);
        if (conversation == null) {
            throw exception(USER_CHAT_CONVERSATION_NOT_EXISTS);
        }
        return conversation;
    }

    @Override
    public UserChatConversationDO getConversationByUserId(Long userId) {
        return conversationMapper.selectByUserId(userId);
    }

}
