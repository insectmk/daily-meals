package cn.iocoder.yudao.module.meals.service.statistics;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.iocoder.yudao.module.meals.controller.app.statistics.vo.AppMsgInteractDataRespVO;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInteractDataRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercomment.UserCommentDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.usercomment.UserCommentMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.userfavor.UserFavorMapper;
import cn.iocoder.yudao.module.meals.enums.ContentTypesEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import cn.iocoder.yudao.module.meals.service.recipe.RecipeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * @Title: CommonServiceImpl
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.service.common
 * @Date 2025/6/30 10:48
 * @description: 统计服务实现
 */
@Service
@Validated
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private UserFavorMapper userFavorMapper;
    @Resource
    private UserCommentMapper userCommentMapper;

    @Override
    public AppMsgInteractDataRespVO getMsgInteractData(Long userId) {
        AppMsgInteractDataRespVO result = new AppMsgInteractDataRespVO();
        // 1 点赞
        result.setLikes(0L);
        // 2 被收藏
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
        // 3 收到的评论
        List<Map<String, Object>> commentsMaps = userCommentMapper.selectMaps(new QueryWrapper<UserCommentDO>()
                .select("count(distinct id) as comments")
                .eq("content_user_id", userId) // 内容所属者为该用户
        );
        if (CollUtil.isEmpty(commentsMaps)) {
            result.setComments(0L);
        } else {
            result.setComments(MapUtil.getLong(commentsMaps.get(0), "comments", 0L));
        }
        // 4 回复我的
        List<Map<String, Object>> receivesMaps = userCommentMapper.selectMaps(new QueryWrapper<UserCommentDO>()
                .select("count(distinct id) as receives")
                .eq("reply_user_id", userId) // 评论回复的人为该用户
        );
        if (CollUtil.isEmpty(receivesMaps)) {
            result.setReceives(0L);
        } else {
            result.setReceives(MapUtil.getLong(receivesMaps.get(0), "receives", 0L));
        }
        return result;
    }
}
