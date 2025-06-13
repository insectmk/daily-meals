package cn.iocoder.yudao.module.meals.service.userchat;

import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessagePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessageSendReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;

import java.util.List;

/**
 * 客服消息 Service 接口
 *
 * @author HUIHUI
 */
public interface AppUserChatMessageService {

    /**
     * 发送消息
     *
     * @param sendReqVO 信息
     */
    void sendMessage(AppUserChatMessageSendReqVO sendReqVO);

    /**
     * 【管理员】更新消息已读状态
     *
     * @param conversationId 会话编号
     * @param userId         用户编号
     * @param userType       用户类型
     */
    void updateUserChatMessageReadStatus(Long conversationId, Long userId, Integer userType);

    /**
     * 获得用户会话消息分页
     *
     * @param pageReqVO 请求
     * @return 客服消息分页
     */
    List<UserChatMessageDO> getUserChatMessageList(AppUserChatMessagePageReqVO pageReqVO);

}
