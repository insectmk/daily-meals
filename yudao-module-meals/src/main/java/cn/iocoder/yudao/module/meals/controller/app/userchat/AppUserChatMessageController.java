package cn.iocoder.yudao.module.meals.controller.app.userchat;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessageListReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessageRespVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message.AppUserChatMessageSendReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatMessageDO;
import cn.iocoder.yudao.module.meals.service.userchat.AppUserChatMessageService;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
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
    private AdminUserApi adminUserApi;

    @PostMapping("/send")
    @Operation(summary = "发送消息")
    public CommonResult<Long> sendUserChatMessage(@Valid @RequestBody AppUserChatMessageSendReqVO sendReqVO) {
        sendReqVO.setSenderUserId(getLoginUserId()); // 设置用户编号
        return success(appUserChatMessageService.sendMessage(sendReqVO));
    }

    @PutMapping("/update-read-status")
    @Operation(summary = "更新客服消息已读状态")
    @Parameter(name = "conversationId", description = "会话编号", required = true)
    public CommonResult<Boolean> updateUserChatMessageReadStatus(@RequestParam("conversationId") Long conversationId) {
        appUserChatMessageService.updateUserChatMessageReadStatus(conversationId, getLoginUserId(), UserTypeEnum.ADMIN.getValue());
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得客服消息列表")
    public CommonResult<List<AppUserChatMessageRespVO>> getUserChatMessageList(@Valid AppUserChatMessageListReqVO pageReqVO) {
        // 获得数据
        List<UserChatMessageDO> list = appUserChatMessageService.getUserChatMessageList(pageReqVO);

        // 拼接数据
        List<AppUserChatMessageRespVO> result = BeanUtils.toBean(list, AppUserChatMessageRespVO.class);
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertSet(result, AppUserChatMessageRespVO::getSenderUserId));
        result.forEach(item -> findAndThen(userMap, item.getSenderUserId(), user -> item.setSenderUserAvatar(user.getAvatar())));
        return success(result);
    }

}
