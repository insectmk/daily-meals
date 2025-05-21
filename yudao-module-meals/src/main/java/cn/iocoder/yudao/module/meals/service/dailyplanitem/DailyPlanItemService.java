package cn.iocoder.yudao.module.meals.service.dailyplanitem;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 每日计划明细 Service 接口
 *
 * @author InsectMk
 */
public interface DailyPlanItemService {

    /**
     * 创建每日计划明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDailyPlanItem(@Valid AppDailyPlanItemSaveReqVO createReqVO);

    /**
     * 更新每日计划明细
     *
     * @param updateReqVO 更新信息
     */
    void updateDailyPlanItem(@Valid AppDailyPlanItemSaveReqVO updateReqVO);

    /**
     * 删除每日计划明细
     *
     * @param id 编号
     */
    void deleteDailyPlanItem(Long id);

    /**
     * 获得每日计划明细
     *
     * @param id 编号
     * @return 每日计划明细
     */
    DailyPlanItemDO getDailyPlanItem(Long id);

    /**
     * 获得每日计划明细分页
     *
     * @param pageReqVO 分页查询
     * @return 每日计划明细分页
     */
    PageResult<DailyPlanItemDO> getDailyPlanItemPage(AppDailyPlanItemPageReqVO pageReqVO);

}