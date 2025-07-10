package cn.iocoder.yudao.module.meals.controller.app.userchat;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation.AppUserChatConversationMarkReadStatusReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation.AppUserChatConversationRespVO;
import cn.iocoder.yudao.module.meals.controller.app.userchat.vo.conversation.AppUserChatConversationUpdatePinnedReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userchat.UserChatConversationDO;
import cn.iocoder.yudao.module.meals.service.userchat.AppUserChatConversationService;
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

@Tag(name = "用户APP - 用户聊天会话")
@RestController
@RequestMapping("/meals/user-chat-conversation")
@Validated
public class AppUserChatConversationController {

    @Resource
    private AppUserChatConversationService appUserChatConversationService;
    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/get")
    @Operation(summary = "获得客服会话")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppUserChatConversationRespVO> getConversation(@RequestParam("id") Long id) {
         UserChatConversationDO conversation = appUserChatConversationService.getConversation(id);
        if (conversation == null) {
            return success(null);
        }

        // 拼接数据
         AppUserChatConversationRespVO result = BeanUtils.toBean(conversation,  AppUserChatConversationRespVO.class);
        MemberUserRespDTO memberUser = memberUserApi.getUser(conversation.getChatUserId());
        if (memberUser != null) {
            result.setChatUserAvatar(memberUser.getAvatar()).setChatUserNickname(memberUser.getNickname());
        }
        return success(result);
    }

    @PutMapping("/update-conversation-pinned")
    @Operation(summary = "置顶/取消置顶会话")
    public CommonResult<Boolean> updateConversationPinned(@Valid @RequestBody AppUserChatConversationUpdatePinnedReqVO updateReqVO) {
        appUserChatConversationService.updateConversationPinned(updateReqVO);
        return success(true);
    }

    @PutMapping("/mark-read-status")
    @Operation(summary = "标记会话未读/已读")
    public CommonResult<Boolean> markReadStatus(@Valid @RequestBody AppUserChatConversationMarkReadStatusReqVO markReqVO) {
        appUserChatConversationService.markReadStatus(markReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客服会话")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteConversation(@RequestParam("id") Long id) {
        appUserChatConversationService.deleteKefuConversation(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得用户会话列表")
    public CommonResult<List<AppUserChatConversationRespVO>> getConversationList() {
        // 查询会话列表
        List<AppUserChatConversationRespVO> respList = BeanUtils.toBean(appUserChatConversationService.getUserChatConversationList(getLoginUserId()),
                 AppUserChatConversationRespVO.class);

        // 拼接聊天对象用户数据
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(respList,  AppUserChatConversationRespVO::getChatUserId));
        respList.forEach(item-> findAndThen(userMap, item.getChatUserId(),
                memberUser-> item.setChatUserAvatar(memberUser.getAvatar()).setChatUserNickname(memberUser.getNickname())));
        return success(respList);
    }

}
