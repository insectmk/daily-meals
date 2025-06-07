package cn.iocoder.yudao.module.meals.dal.mysql.dailyplan;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.*;

/**
 * 菜谱计划 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface DailyPlanMapper extends BaseMapperX<DailyPlanDO> {

    default PageResult<DailyPlanDO> selectPage(AppDailyPlanPageReqVO reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DailyPlanDO>()
                .and(wrapper -> wrapper
                    .eq(DailyPlanDO::getUserId, userId)
                    .or()
                    .isNull(DailyPlanDO::getUserId)
                )
                .betweenIfPresent(DailyPlanDO::getPlanDate, reqVO.getPlanDate())
                .eqIfPresent(DailyPlanDO::getMemo, reqVO.getMemo())
                .betweenIfPresent(DailyPlanDO::getCreateTime, reqVO.getCreateTime())
                // 有计划明细的
                .apply("exists(select 1 from meals_daily_plan_item i where i.plan_id = meals_daily_plan.id and i.deleted = 0)")
                .orderByDesc(DailyPlanDO::getPlanDate));
    }

    default PageResult<DailyPlanDO> selectDayGroupPage(AppDailyPlanPageReqVO reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DailyPlanDO>()
                .eqIfPresent(DailyPlanDO::getUserId, userId)
                .betweenIfPresent(DailyPlanDO::getPlanDate, reqVO.getPlanDate())
                .eqIfPresent(DailyPlanDO::getMemo, reqVO.getMemo())
                .betweenIfPresent(DailyPlanDO::getCreateTime, reqVO.getCreateTime())
                .select(DailyPlanDO::getPlanDate)
                .orderByDesc(DailyPlanDO::getPlanDate)
                .orderByDesc(DailyPlanDO::getPlanDate));
    }

}
