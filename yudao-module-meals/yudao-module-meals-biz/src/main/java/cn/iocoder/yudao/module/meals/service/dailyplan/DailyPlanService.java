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
     * @param userId 分页查询
     * @param pageReqVO 菜谱计划分页
     * @return
     */
    PageResult<DailyPlanDO> getDailyPlanPage(Long userId, @Valid AppDailyPlanPageReqVO pageReqVO);

    /**
     * 将菜谱加入到当天的计划
     * @param createReqVO 菜谱信息
     * @param loginUserId 用户ID
     * @return
     */
    List<Long> addRecipesTodayPlan(@Valid AppDailyPlanRecipeSaveTodayReqVO createReqVO, Long loginUserId);
}
