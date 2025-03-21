package cn.iocoder.yudao.module.meals.service.dailyplan;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.dailyplan.DailyPlanMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

/**
 * 菜谱计划 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class DailyPlanServiceImpl implements DailyPlanService {

    @Resource
    private DailyPlanMapper dailyPlanMapper;

    @Override
    public Long createDailyPlan(AppDailyPlanSaveReqVO createReqVO) {
        // 插入
        DailyPlanDO dailyPlan = BeanUtils.toBean(createReqVO, DailyPlanDO.class);
        dailyPlanMapper.insert(dailyPlan);
        // 返回
        return dailyPlan.getId();
    }

    @Override
    public void updateDailyPlan(AppDailyPlanSaveReqVO updateReqVO) {
        // 校验存在
        validateDailyPlanExists(updateReqVO.getId());
        // 更新
        DailyPlanDO updateObj = BeanUtils.toBean(updateReqVO, DailyPlanDO.class);
        dailyPlanMapper.updateById(updateObj);
    }

    @Override
    public void deleteDailyPlan(Long id) {
        // 校验存在
        validateDailyPlanExists(id);
        // 删除
        dailyPlanMapper.deleteById(id);
    }

    private void validateDailyPlanExists(Long id) {
        if (dailyPlanMapper.selectById(id) == null) {
            throw exception(DAILY_PLAN_NOT_EXISTS);
        }
    }

    @Override
    public DailyPlanDO getDailyPlan(Long id) {
        return dailyPlanMapper.selectById(id);
    }

    @Override
    public PageResult<DailyPlanDO> getDailyPlanPage(Long userId,AppDailyPlanPageReqVO pageReqVO) {
        return dailyPlanMapper.selectPage(userId, pageReqVO);
    }

}
