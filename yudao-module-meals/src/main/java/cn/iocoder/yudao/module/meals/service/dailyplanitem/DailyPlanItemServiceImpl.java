package cn.iocoder.yudao.module.meals.service.dailyplanitem;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.dailyplanitem.DailyPlanItemMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

/**
 * 每日计划明细 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class DailyPlanItemServiceImpl implements DailyPlanItemService {

    @Resource
    private DailyPlanItemMapper dailyPlanItemMapper;

    @Override
    public Long createDailyPlanItem(AppDailyPlanItemSaveReqVO createReqVO) {
        // 插入
        DailyPlanItemDO dailyPlanItem = BeanUtils.toBean(createReqVO, DailyPlanItemDO.class);
        dailyPlanItemMapper.insert(dailyPlanItem);
        // 返回
        return dailyPlanItem.getId();
    }

    @Override
    public void updateDailyPlanItem(AppDailyPlanItemSaveReqVO updateReqVO) {
        // 校验存在
        validateDailyPlanItemExists(updateReqVO.getId());
        // 更新
        DailyPlanItemDO updateObj = BeanUtils.toBean(updateReqVO, DailyPlanItemDO.class);
        dailyPlanItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteDailyPlanItem(Long id) {
        // 校验存在
        validateDailyPlanItemExists(id);
        // 删除
        dailyPlanItemMapper.deleteById(id);
    }

    private void validateDailyPlanItemExists(Long id) {
        if (dailyPlanItemMapper.selectById(id) == null) {
            throw exception(DAILY_PLAN_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public DailyPlanItemDO getDailyPlanItem(Long id) {
        return dailyPlanItemMapper.selectById(id);
    }

    @Override
    public PageResult<DailyPlanItemDO> getDailyPlanItemPage(AppDailyPlanItemPageReqVO pageReqVO) {
        return dailyPlanItemMapper.selectPage(pageReqVO);
    }

}