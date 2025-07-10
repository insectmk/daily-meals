package cn.iocoder.yudao.module.meals.service.userchat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.infra.api.websocket.WebSocketSenderApi;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessagePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessageRespVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessageSendReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatUnreadMessageCntRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatConversationDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;
import cn.iocoder.yudao.module.meals.dal.mysql.userchat.UserChatConversationMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.userchat.UserChatMessageMapper;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.getFirst;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.USER_CHAT_CONVERSATION_NOT_EXISTS;
import static cn.iocoder.yudao.module.meals.enums.WebSocketMessageTypeConstants.USER_CHAT_MESSAGE_READ_STATUS_CHANGE;
import static cn.iocoder.yudao.module.meals.enums.WebSocketMessageTypeConstants.USER_CHAT_MESSAGE_TYPE;

/**
 * 客服消息 Service 实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class AppUserChatMessageServiceImpl implements AppUserChatMessageService {

    @Resource
    private UserChatMessageMapper userChatMessageMapper;
    @Resource
    private AppUserChatConversationService conversationService;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private WebSocketSenderApi webSocketSenderApi;
    @Resource
    private UserChatConversationMapper conversationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(AppUserChatMessageSendReqVO sendReqVO) {
        // 1、发送方会话
        // 1.1 设置会话编号
         UserChatMessageDO userChatMessage = BeanUtils.toBean(sendReqVO,  UserChatMessageDO.class);
         UserChatConversationDO conversation = conversationService.getOrCreateConversation(sendReqVO.getSenderUserId(), sendReqVO.getReceiverUserId());
        userChatMessage.setConversationId(conversation.getId());
        // 1.2 判断是否隐藏会话，如果隐藏，则开启
        if (conversation.getUserDeleted()) {
            conversationMapper.updateById(new UserChatConversationDO()
                    .setId(conversation.getId())
                    .setUserDeleted(Boolean.FALSE));
        }
        // 1.3 保存消息
        userChatMessageMapper.insert(userChatMessage);
        // 1.4 更新会话消息冗余
        conversationService.updateConversationLastMessage(userChatMessage);
        // 2、接收方会话
        // 2.1 设置会话编码
        UserChatConversationDO receiveConversation = conversationService.getOrCreateConversation(sendReqVO.getReceiverUserId(), sendReqVO.getSenderUserId());
        // 2.2 判断是否隐藏会话，如果隐藏，则开启
        if (receiveConversation.getUserDeleted()) {
            conversationMapper.updateById(new UserChatConversationDO()
                    .setId(receiveConversation.getId())
                    .setUserDeleted(Boolean.FALSE));
        }
        // 2.3 保存消息
        userChatMessage.setId(null); // 清空ID
        userChatMessage.setConversationId(receiveConversation.getId()); // 设置会话编码
        userChatMessageMapper.insert(userChatMessage);
        // 2.4 更新会话消息冗余
        conversationService.updateConversationLastMessage(userChatMessage);
        // 2.5 更新未读消息数
        conversationMapper.updateUnreadMessageCountIncrement(receiveConversation.getId());
        // 3 通知用户对话更新
        // 3.1 查询用户信息
        MemberUserRespDTO senderUser = memberUserApi.getUser(userChatMessage.getSenderUserId());
        // 3.2 拼装用户信息
        AppUserChatMessageRespVO userChatMessageNotice = BeanUtils.toBean(sendReqVO, AppUserChatMessageRespVO.class);
        userChatMessageNotice.setSenderUserAvatar(senderUser.getAvatar()); // 头像
        userChatMessageNotice.setCreateTime(LocalDateTime.now()); // 消息创建时间
        getSelf().sendAsyncMessageToMember(sendReqVO.getReceiverUserId(),USER_CHAT_MESSAGE_TYPE, userChatMessageNotice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserChatMessageReadStatus(Long conversationId, Long userId, Integer userType) {
        // 1.1 校验会话是否存在
         UserChatConversationDO conversation = conversationService.validateConversationExists(conversationId);
        // 1.2 如果是会员端处理已读，需要传递 userId；万一用户模拟一个 conversationId
        if (UserTypeEnum.MEMBER.getValue().equals(userType) && ObjUtil.notEqual(conversation.getUserId(), userId)) {
            throw exception(USER_CHAT_CONVERSATION_NOT_EXISTS);
        }
        // 1.3 查询会话所有的未读消息 (tips: 多个客服，一个人点了，就都点了)
        List<UserChatMessageDO> messageList = userChatMessageMapper.selectListByConversationIdAndUserTypeAndReadStatus(conversationId, userType, Boolean.FALSE);
        if (CollUtil.isEmpty(messageList)) {
            return;
        }

        // 2.1 情况二：更新未读消息状态为已读
        userChatMessageMapper.updateReadStatusBatchByIds(convertSet(messageList,  UserChatMessageDO::getId),
                new UserChatMessageDO().setReadStatus(Boolean.TRUE));
        // 2.2 将管理员未读消息计数更新为零
        conversationService.updateAdminUnreadMessageCountToZero(conversationId);

        // 2.3 发送消息通知会员，管理员已读 -> 会员更新发送的消息状态
         UserChatMessageDO keFuMessage = getFirst(messageList);
        assert keFuMessage != null; // 断言避免警告
        getSelf().sendAsyncMessageToMember(keFuMessage.getSenderUserId(), USER_CHAT_MESSAGE_READ_STATUS_CHANGE, conversation.getId());
        // 2.4 通知所有管理员消息已读
        getSelf().sendAsyncMessageToMember(userId,USER_CHAT_MESSAGE_READ_STATUS_CHANGE, conversation.getId());
    }

    private void validateReceiverExist(Long receiverId, Integer receiverType) {
        if (UserTypeEnum.ADMIN.getValue().equals(receiverType)) {
            adminUserApi.validateUser(receiverId);
        }
        if (UserTypeEnum.MEMBER.getValue().equals(receiverType)) {
            memberUserApi.validateUser(receiverId);
        }
    }

    /**
     * 发送WebSocket消息给用户
     * @param userId 用户ID
     * @param messageType 消息类型
     * @param content 消息内容
     */
    @Async
    public void sendAsyncMessageToMember(Long userId, String messageType, Object content) {
        webSocketSenderApi.sendObject(UserTypeEnum.MEMBER.getValue(), userId, messageType, content);
    }

    @Override
    public List<UserChatMessageDO> getUserChatMessageList(AppUserChatMessagePageReqVO pageReqVO) {
        // 1. 获得用户消息会话
        UserChatConversationDO conversation = conversationService.getConversationByUserId(pageReqVO.getSenderUserId(), pageReqVO.getReceiverUserId());
        if (conversation == null) {
            return Collections.emptyList();
        }
        // 2. 设置会话编号
        pageReqVO.setConversationId(conversation.getId());
        return userChatMessageMapper.selectList(pageReqVO);
    }

    @Override
    public List<AppUserChatUnreadMessageCntRespVO> getUserChatUnreadMessageCountList(Long userId) {
        // SQL sum 查询
        List<Map<String, Object>> result = conversationMapper.selectMaps(new QueryWrapper<UserChatConversationDO>()
                .select(String.format("id, (select count(*) from meals_user_chat_message m where m.conversation_id = meals_user_chat_conversation.id and m.receiver_user_id = %d and m.read_status = 0 and deleted = 0) AS unreadCount", userId))
                .eq("user_id", userId)
        );
        // 使用convertListByFlatMap转换结果
        return CollectionUtils.convertListByFlatMap(result, record -> {
            // 从Map中提取字段并构建对象
            Long conversationId = MapUtil.getLong(record, "id"); // 会话ID
            Integer unreadCount = MapUtil.getInt(record, "unreadCount"); // 未读消息数

            // 过滤空值并创建流
            if (conversationId == null || unreadCount == null) {
                return Stream.empty();
            }
            AppUserChatUnreadMessageCntRespVO unreadMsgCntVO = new AppUserChatUnreadMessageCntRespVO();
            unreadMsgCntVO.setConversationId(conversationId); // 设置会话ID
            unreadMsgCntVO.setUnreadCount(unreadCount); // 设置未读消息数
            return Stream.of(unreadMsgCntVO);
        });
    }

    @Override
    public void conversationRead(Long conversationId, Long userId) {
        // 1 已读当前会话 接收的 内容
        userChatMessageMapper.update(new UserChatMessageDO()
                .setReadStatus(Boolean.TRUE),
                new LambdaQueryWrapperX<UserChatMessageDO>()
                        .eq(UserChatMessageDO::getConversationId, conversationId) // 该会话
                        .eq(UserChatMessageDO::getReceiverUserId, userId) // 该用户接收的内容
                        .eq(UserChatMessageDO::getReadStatus, Boolean.FALSE) // 未读的数据
        );
        // 2 已读聊天对象会话的内容
        // 2.1 查询出聊天对象的会话
        UserChatConversationDO conversationDO = conversationMapper.selectById(conversationId); // 发送方会话
        UserChatConversationDO receiverConversationDO = conversationService.getOrCreateConversation(conversationDO.getChatUserId(), conversationDO.getUserId()); // 接收方会话
        // 2.2 已读接收方会话 发送的内容
        userChatMessageMapper.update(new UserChatMessageDO()
                        .setReadStatus(Boolean.TRUE),
                new LambdaQueryWrapperX<UserChatMessageDO>()
                        .eq(UserChatMessageDO::getConversationId, receiverConversationDO.getId()) // 该会话
                        .eq(UserChatMessageDO::getSenderUserId, userId) // 该会话用户发送的内容
                        .eq(UserChatMessageDO::getReadStatus, Boolean.FALSE) // 未读的数据
        );
        // 3 通知接收方，已读消息
        getSelf().sendAsyncMessageToMember(receiverConversationDO.getUserId(),USER_CHAT_MESSAGE_READ_STATUS_CHANGE, null);
    }

    @Override
    public void conversationReadByReceiver(Long receiverUserId, Long senderUserId) {
        UserChatConversationDO conversationDO = conversationService.getOrCreateConversation(senderUserId, receiverUserId); // 获取会话
        conversationRead(conversationDO.getId(), senderUserId); // 已读内容
    }

    private AppUserChatMessageServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
