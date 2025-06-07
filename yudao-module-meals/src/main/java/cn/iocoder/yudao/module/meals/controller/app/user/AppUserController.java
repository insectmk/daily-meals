package cn.iocoder.yudao.module.meals.controller.app.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInfoRespVO;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInteractDataRespVO;
import cn.iocoder.yudao.module.meals.service.user.UserService;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Title: AppUserController
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.controller.app.user
 * @Date 2025/6/5 16:56
 * @description: 用户 APP - 用户
 */
@Tag(name = "用户 APP - 菜谱")
@RestController
@RequestMapping("/meals/user")
@Validated
public class AppUserController {
    @Resource
    private UserService userService ;

    @GetMapping("/page")
    @Operation(summary = "分页查询用户")
    public CommonResult<PageResult<AppUserInfoRespVO>> getUsersPage(@Valid MemberUserPageReqVO reqVO) {
        return success(userService.getUsersPage(getLoginUserId(), reqVO));
    }

    @GetMapping("/page-follows")
    @Operation(summary = "分页查询关注的用户")
    public CommonResult<PageResult<AppUserInfoRespVO>> getFollowUsersPage(@Valid MemberUserPageReqVO reqVO) {
        return success(userService.getFollowUsersPage(getLoginUserId(), reqVO));
    }

    @GetMapping("/page-fans")
    @Operation(summary = "分页查询粉丝用户")
    public CommonResult<PageResult<AppUserInfoRespVO>> getFanUsersPage(@Valid MemberUserPageReqVO reqVO) {
        return success(userService.getFanUsersPage(getLoginUserId(), reqVO));
    }

    @GetMapping("/user-interact-data")
    @Operation(summary = "获取用户的关注数、粉丝数、获赞与收藏量")
    public CommonResult<AppUserInteractDataRespVO> getUserInteractData(@RequestParam("userId") Long userId) {
        return success(userService.getUserInteractData(userId));
    }
}
