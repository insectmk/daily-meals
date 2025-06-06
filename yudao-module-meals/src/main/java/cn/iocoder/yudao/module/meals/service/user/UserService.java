package cn.iocoder.yudao.module.meals.service.user;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInfoRespVO;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInteractDataRespVO;
import cn.iocoder.yudao.module.member.controller.admin.user.vo.MemberUserPageReqVO;

import java.util.List;

/**
 * @Title: UserService
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.service.user
 * @Date 2025/6/6 10:24
 * @description: 用户服务类
 */
public interface UserService {
    /**
     * 获取用户互动数据
     * 获取用户的关注数、粉丝数、获赞与收藏量
     * @param userId 用户ID
     * @return 关注数、粉丝数、获赞与收藏量
     */
    AppUserInteractDataRespVO getUserInteractData(String userId);

    /**
     * 基于用户昵称，模糊匹配用户列表
     * @param loginUserId 当前登录用户id
     * @param reqVO 分页请求参数
     * @return 用户列表
     */
    PageResult<AppUserInfoRespVO> getUsersPage(Long loginUserId, MemberUserPageReqVO reqVO);
}
