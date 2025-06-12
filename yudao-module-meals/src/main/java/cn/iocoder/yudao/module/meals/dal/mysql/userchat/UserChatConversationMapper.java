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

    default void updateAdminUnreadMessageCountIncrement(Long id) {
        update(new LambdaUpdateWrapper<UserChatConversationDO>()
                .eq(UserChatConversationDO::getId, id)
                .setSql("admin_unread_message_count = admin_unread_message_count + 1"));
    }

    default UserChatConversationDO selectByUserId(Long userId) {
        return selectOne(UserChatConversationDO::getUserId, userId);
    }

}
