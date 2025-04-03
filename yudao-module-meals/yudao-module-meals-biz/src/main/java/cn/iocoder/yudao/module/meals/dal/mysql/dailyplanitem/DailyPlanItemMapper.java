package cn.iocoder.yudao.module.meals.dal.mysql.dailyplanitem;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.*;

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

}