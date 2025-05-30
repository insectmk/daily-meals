package cn.iocoder.yudao.module.meals.convert.food;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.food.vo.FoodSimpleRespVO;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.AppDailyPlanItemDetailRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDetailDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMultiMap;

/**
 * @Title: DailyPlanConvert
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.convert.recipe.dailyplan
 * @Date 2025/4/3 17:00
 * @description: 食材对象转换
 */
@Mapper
public interface FoodConvert {
    FoodConvert INSTANCE = Mappers.getMapper(FoodConvert.class);

    /**
     * 将食材信息转为食材精简信息列表
     * @param list
     * @return
     */
    List<FoodSimpleRespVO> convertSimpleList(List<FoodDO> list);
}
