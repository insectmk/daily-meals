package cn.iocoder.yudao.module.meals.dal.mysql.dailyplan;

import java.util.*;

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
                .eqIfPresent(DailyPlanDO::getRecipeId, reqVO.getRecipeId())
                .betweenIfPresent(DailyPlanDO::getPlanDate, reqVO.getPlanDate())
                .eqIfPresent(DailyPlanDO::getMealType, reqVO.getMealType())
                .eqIfPresent(DailyPlanDO::getMemo, reqVO.getMemo())
                .betweenIfPresent(DailyPlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DailyPlanDO::getId));
    }

}
