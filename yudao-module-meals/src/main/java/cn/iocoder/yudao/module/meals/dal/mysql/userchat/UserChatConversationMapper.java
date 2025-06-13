package cn.iocoder.yudao.module.meals.dal.mysql.userchat;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatConversationDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客服会话 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface UserChatConversationMapper extends BaseMapperX<UserChatConversationDO> {

    default List<UserChatConversationDO> selectConversationList() {
        return selectList(new LambdaQueryWrapperX<UserChatConversationDO>()
                .eq(UserChatConversationDO::getUserDeleted, Boolean.FALSE)
                .orderByDesc(UserChatConversationDO::getCreateTime));
    }

    /**
     * 更新会话消息未读数
     * @param id 会话ID
     */
    default void updateUnreadMessageCountIncrement(Long id) {
        update(new LambdaUpdateWrapper<UserChatConversationDO>()
                .eq(UserChatConversationDO::getId, id)
                .setSql("unread_message_count = unread_message_count + 1"));
    }

    /**
     * 获取用户会话
     * @param senderUserId 发送方ID
     * @param receiverUserId 接收方ID
     * @return 用户会话
     */
    default UserChatConversationDO selectByUserId(Long senderUserId, Long receiverUserId) {
        return selectOne(
                UserChatConversationDO::getUserId, senderUserId,
                UserChatConversationDO::getChatUserId, receiverUserId);
    }

}
