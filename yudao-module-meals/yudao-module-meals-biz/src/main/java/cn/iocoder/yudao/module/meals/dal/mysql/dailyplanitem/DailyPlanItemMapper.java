package cn.iocoder.yudao.module.meals.dal.mysql.dailyplanitem;

import cn.hutool.core.map.MapUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.AppDailyPlanItemPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePopularPublicReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.PopularPublicRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 每日计划明细 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface DailyPlanItemMapper extends BaseMapperX<DailyPlanItemDO> {

    default PageResult<DailyPlanItemDO> selectPage(AppDailyPlanItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DailyPlanItemDO>()
                .eqIfPresent(DailyPlanItemDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(DailyPlanItemDO::getRecipeId, reqVO.getRecipeId())
                .eqIfPresent(DailyPlanItemDO::getMealType, reqVO.getMealType())
                .eqIfPresent(DailyPlanItemDO::getMemo, reqVO.getMemo())
                .betweenIfPresent(DailyPlanItemDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DailyPlanItemDO::getId));
    }

    /**
     * 获得最热门的菜谱
     * @param reqVO
     * @return
     */
    default List<PopularPublicRecipeDO> selectPopularPublicRecipeList(AppRecipePopularPublicReqVO reqVO) {
        List<Map<String, Object>> maps = selectMaps(new MPJLambdaWrapperX<DailyPlanItemDO>()
                .select(DailyPlanItemDO::getRecipeId)
                .select(DailyPlanItemDO::getMealType)
                // 添加统计字段
                .select("COUNT(*) AS count")
                // 加入菜谱判断是否为空用户的
                .leftJoin(RecipeDO.class, RecipeDO::getId, DailyPlanItemDO::getRecipeId)
                // 用户ID为空的
                .isNull(RecipeDO::getUserId)
                // 指定餐次类型
                .eq(DailyPlanItemDO::getMealType, reqVO.getMealType())
                // 按照菜谱、餐次类型分组
                .groupBy(DailyPlanItemDO::getRecipeId, DailyPlanItemDO::getMealType)
                // 新增排序规则（按count升序）
                .orderByAsc("count")
                // 新增限制结果数量（取前5条）
                .last(String.format("LIMIT %d", reqVO.getRankLen())));

        // 使用convertListByFlatMap转换结果
        return CollectionUtils.convertListByFlatMap(maps, record -> {
            // 从Map中提取字段并构建PopularPublicRecipeDO对象
            Long recipeId = MapUtil.getLong(record, "recipe_id");
            Integer mealType = MapUtil.getInt(record, "meal_type");
            Integer count = MapUtil.getInt(record, "count");

            // 过滤空值并创建流
            if (recipeId == null || mealType == null || count == null) {
                return Stream.empty();
            }

            return Stream.of(new PopularPublicRecipeDO(recipeId, mealType, count));
        });
    }
}
