package cn.iocoder.yudao.module.meals.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInfoRespVO;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInteractDataRespVO;
import cn.iocoder.yudao.module.meals.convert.user.UserConvert;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.dal.mysql.userfavor.UserFavorMapper;
import cn.iocoder.yudao.module.meals.enums.ContentTypesEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    private MemberUserMapper memberUserMapper;
    @Resource
    private UserFavorMapper userFavorMapper;

    @Override
    public AppUserInteractDataRespVO getUserInteractData(String userId) {
        AppUserInteractDataRespVO result = new AppUserInteractDataRespVO();
        // 关注
        List<Map<String, Object>> followsMaps = userFavorMapper.selectMaps(new QueryWrapper<UserFavorDO>()
                .select("count(distinct content_id) as follows")
                .eq("content_type", ContentTypesEnum.USER.getType()) // 用户类型
                .eq("user_id", userId) // 该用户的
        );
        if (CollUtil.isEmpty(followsMaps)) {
            result.setFollows(0L);
        } else {
            result.setFollows(MapUtil.getLong(followsMaps.get(0), "follows", 0L));
        }
        // 粉丝
        List<Map<String, Object>> fansMaps = userFavorMapper.selectMaps(new QueryWrapper<UserFavorDO>()
                .select("count(distinct user_id) as fans")
                .eq("content_type", ContentTypesEnum.USER.getType()) // 用户类型
                .eq("content_id", userId) // 关注为该用户的
        );
        if (CollUtil.isEmpty(fansMaps)) {
            result.setFans(0L);
        } else {
            result.setFans(MapUtil.getLong(fansMaps.get(0), "fans", 0L));
        }
        // 点赞
        result.setLikes(0L);
        // 被收藏
        List<Map<String, Object>> collectMaps = userFavorMapper.selectMaps(new QueryWrapper<UserFavorDO>()
                .select("count(distinct content_id,user_id) as collects")
                .eq("content_type", ContentTypesEnum.RECIPE.getType()) // 菜谱类型
                .apply(String.format("exists(select 1 from meals_recipe r where r.user_id = '%s' and recipe_type = %d  and r.id = meals_user_favor.content_id and deleted = 0)", userId, RecipeTypesEnum.USER.getType())) // 收藏为该用户的
        );
        if (CollUtil.isEmpty(collectMaps)) {
            result.setCollects(0L);
        } else {
            result.setCollects(MapUtil.getLong(collectMaps.get(0), "collects", 0L));
        }
        return result;
    }

    @Override
    public PageResult<AppUserInfoRespVO> getUsersPage(Long loginUserId, MemberUserPageReqVO reqVO) {
        // 查询用户分页
        PageResult<MemberUserDO> userDOPageResult = memberUserMapper.selectPage(reqVO);
        // 查询是否关注数据
        Set<Long> contentIds = convertSet(userDOPageResult.getList(), MemberUserDO::getId);
        Set<Long> favorContentIds = userFavorMapper.getFavorContentIds(contentIds, ContentTypesEnum.USER.getType(), loginUserId);
        return UserConvert.INSTANCE.convertFavorPage(userDOPageResult, favorContentIds);
    }
}
