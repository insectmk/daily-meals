package cn.iocoder.yudao.module.meals.service.user;

import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInteractDataRespVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @Title: UserServiceImpl
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.service.user
 * @Date 2025/6/6 10:25
 * @description: 用户服务
 */
@Service
@Validated
public class UserServiceImpl implements UserService {
    @Override
    public AppUserInteractDataRespVO getUserInteractData(String userId) {
        AppUserInteractDataRespVO result = new AppUserInteractDataRespVO();
        // todo 需要完善
        // 关注
        result.setFollows(178L);
        // 粉丝
        result.setFans(123L);
        // 点赞
        result.setLikes(267L);
        // 收藏
        result.setCollects(2738L);
        return result;
    }
}
