package cn.iocoder.yudao.module.meals.convert.dailyplan;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.AppDailyPlanItemDetailRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDetailDO;
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
 * @description: 每日计划对象转换
 */
@Mapper
public interface DailyPlanConvert {
    DailyPlanConvert INSTANCE = Mappers.getMapper(DailyPlanConvert.class);

    /**
     * 将普通的计划分页，转换为带详细信息的计划分页
     * @param pageResult 原始分页对象
     * @param planItems 计划明细信息
     * @return 带详细信息的计划分页对象
     */
    default PageResult<AppDailyPlanDetailRespVO> convertPage(PageResult<DailyPlanDO> pageResult, List<DailyPlanItemDetailDO> planItems) {
        // 转化为目标类型的Page分页对象
        PageResult<AppDailyPlanDetailRespVO> result = BeanUtils.toBean(pageResult, AppDailyPlanDetailRespVO.class);
        // 获取所有的计划
        List<AppDailyPlanDetailRespVO> plans = result.getList();
        // 将对应的计划明细信息装载到对应的计划上
        Map<Long, List<DailyPlanItemDetailDO>> itemDetailDOMap = convertMultiMap(planItems, DailyPlanItemDetailDO::getPlanId); // key：食谱ID，value：食材信息集合
        plans.forEach(plan -> {
            // 将食材信息装载到菜谱上
            plan.setItems(BeanUtils.toBean(itemDetailDOMap.get(plan.getId()), AppDailyPlanItemDetailRespVO.class));
        });
        return result;
    }
}
