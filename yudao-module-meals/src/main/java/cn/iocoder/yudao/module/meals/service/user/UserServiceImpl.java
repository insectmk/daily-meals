package cn.iocoder.yudao.module.meals.service.user;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInfoRespVO;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInteractDataRespVO;
import cn.iocoder.yudao.module.meals.convert.user.UserConvert;
import cn.iocoder.yudao.module.meals.dal.mysql.userfavor.UserFavorMapper;
import cn.iocoder.yudao.module.meals.enums.ContentTypesEnum;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

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
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private UserFavorMapper userFavorMapper;

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

    @Override
    public List<AppUserInfoRespVO> getUserListByNickname(Long loginUserId, String nickname) {
        List<MemberUserRespDTO> userDtoList = memberUserApi.getUserListByNickname(nickname);
        // 查询是否关注数据
        Set<Long> contentIds = convertSet(userDtoList, MemberUserRespDTO::getId);
        Set<Long> favorContentIds = userFavorMapper.getFavorContentIds(contentIds, ContentTypesEnum.USER.getType(), loginUserId);
        return UserConvert.INSTANCE.convertFavorList(userDtoList, favorContentIds);
    }
}
