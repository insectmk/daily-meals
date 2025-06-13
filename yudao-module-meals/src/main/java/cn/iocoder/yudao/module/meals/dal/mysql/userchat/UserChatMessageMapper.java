package cn.iocoder.yudao.module.meals.dal.mysql.userchat;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessagePageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 客服消息 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface UserChatMessageMapper extends BaseMapperX<UserChatMessageDO> {

    /**
     * 获得消息列表
     * 1. 第一次查询时，不带时间，默认查询最新的十条消息
     * 2. 第二次查询时，带时间，查询历史消息
     *
     * @param reqVO 列表请求
     * @return 消息列表
     */
    default List<UserChatMessageDO> selectList(AppUserChatMessagePageReqVO reqVO) {
        return selectList(new QueryWrapperX<UserChatMessageDO>()
                .eqIfPresent("conversation_id", reqVO.getConversationId())
                .ltIfPresent("create_time", reqVO.getCreateTime())
                .orderByDesc("create_time")
                .limitN(reqVO.getLimit()));
    }

    default List<UserChatMessageDO> selectListByConversationIdAndUserTypeAndReadStatus(Long conversationId, Integer userType,
                                                                                   Boolean readStatus) {
        return selectList(new LambdaQueryWrapper<UserChatMessageDO>()
                .eq(UserChatMessageDO::getConversationId, conversationId)
                .eq(UserChatMessageDO::getReadStatus, readStatus));
    }

    default void updateReadStatusBatchByIds(Collection<Long> ids, UserChatMessageDO keFuMessageDO) {
        update(keFuMessageDO, new LambdaUpdateWrapper<UserChatMessageDO>()
                .in(UserChatMessageDO::getId, ids));
    }

}
