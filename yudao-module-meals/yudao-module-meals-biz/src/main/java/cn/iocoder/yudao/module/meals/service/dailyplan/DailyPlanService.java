package cn.iocoder.yudao.module.meals.service.dailyplan;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 菜谱计划 Service 接口
 *
 * @author InsectMk
 */
public interface DailyPlanService {

    /**
     * 创建菜谱计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDailyPlan(@Valid AppDailyPlanSaveReqVO createReqVO);

    /**
     * 更新菜谱计划
     *
     * @param updateReqVO 更新信息
     */
    void updateDailyPlan(@Valid AppDailyPlanSaveReqVO updateReqVO);

    /**
     * 删除菜谱计划
     *
     * @param id 编号
     */
    void deleteDailyPlan(Long id);

    /**
     * 获得菜谱计划
     *
     * @param id 编号
     * @return 菜谱计划
     */
    DailyPlanDO getDailyPlan(Long id);

    /**
     * 获得菜谱计划分页
     *
     * @param pageReqVO 分页查询
     * @return 菜谱计划分页
     */
    PageResult<DailyPlanDO> getDailyPlanPage(AppDailyPlanPageReqVO pageReqVO);

}