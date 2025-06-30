package cn.iocoder.yudao.module.meals.controller.app.userchat;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;
import cn.iocoder.yudao.module.meals.service.userchat.AppUserChatMessageService;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.framework.common.util.collection.MapUtils.findAndThen;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户APP - 用户聊天消息")
@RestController
@RequestMapping("/meals/user-chat-message")
@Validated
public class AppUserChatMessageController {

    @Resource
    private AppUserChatMessageService appUserChatMessageService;
    @Resource
    private MemberUserApi memberUserApi;

    @PostMapping("/send")
    @Operation(summary = "发送消息")
    public CommonResult<Boolean> sendUserChatMessage(@Valid @RequestBody AppUserChatMessageSendReqVO sendReqVO) {
        sendReqVO.setSenderUserId(getLoginUserId()); // 设置用户编号
        appUserChatMessageService.sendMessage(sendReqVO); // 发送消息
        return success(Boolean.TRUE);
    }

    @PutMapping("/update-read-status")
    @Operation(summary = "更新客服消息已读状态")
    @Parameter(name = "conversationId", description = "会话编号", required = true)
    public CommonResult<Boolean> updateUserChatMessageReadStatus(@RequestParam("conversationId") Long conversationId) {
        appUserChatMessageService.updateUserChatMessageReadStatus(conversationId, getLoginUserId(), UserTypeEnum.ADMIN.getValue());
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得用户聊天消息列表")
    public CommonResult<List<AppUserChatMessageRespVO>> getUserChatMessageList(@Valid AppUserChatMessagePageReqVO pageReqVO) {
        pageReqVO.setSenderUserId(getLoginUserId()); // 设置发送用户ID
        // 1 获得会话消息数据
        List<UserChatMessageDO> list = appUserChatMessageService.getUserChatMessageList(pageReqVO);
        // 2 拼接用户数据
        List<AppUserChatMessageRespVO> result = BeanUtils.toBean(list, AppUserChatMessageRespVO.class);
        // 2.1 获取用户信息
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(result, AppUserChatMessageRespVO::getSenderUserId));
        // 2.2 拼接用户信息
        result.forEach(item -> findAndThen(userMap, item.getSenderUserId(), user -> item.setSenderUserAvatar(user.getAvatar())));
        return success(result);
    }

    @GetMapping("/unread-count-list")
    @Operation(summary = "获得用户未读消息数信息")
    public CommonResult<List<AppUserChatUnreadMessageCntRespVO>> getUserChatUnreadMessageCountList() {
        return success(appUserChatMessageService.getUserChatUnreadMessageCountList(getLoginUserId()));
    }

    @PutMapping("/conversation-read")
    @Operation(summary = "已读会话消息")
    public CommonResult<Boolean> conversationRead(@Valid @RequestBody AppUserChatMessageReadVO reqVO) {
        appUserChatMessageService.conversationRead(reqVO.getConversationId(), getLoginUserId());
        return success(Boolean.TRUE);
    }

    @PutMapping("/conversation-read-by-receiver")
    @Operation(summary = "根据接收方ID已读会话消息")
    public CommonResult<Boolean> conversationReadByReceiver(@Valid @RequestBody AppUserChatMessageReadByReceiverVO reqVO) {
        appUserChatMessageService.conversationReadByReceiver(reqVO.getReceiverUserId(), getLoginUserId());
        return success(Boolean.TRUE);
    }
}
