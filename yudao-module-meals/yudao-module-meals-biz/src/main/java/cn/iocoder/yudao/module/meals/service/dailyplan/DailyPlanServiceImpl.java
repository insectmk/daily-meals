package cn.iocoder.yudao.module.meals.service.dailyplan;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public PageResult<DailyPlanDO> getDailyPlanPage(AppDailyPlanPageReqVO pageReqVO, Long userId) {
        return dailyPlanMapper.selectPage(pageReqVO, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> addRecipesTodayPlan(AppDailyPlanRecipeSaveTodayReqVO createReqVO, Long loginUserId) {
        List<Long> planIds = new ArrayList<>();
        LocalDateTime startDate = LocalDate.now().atStartOfDay(); // 开始时间
        LocalDateTime endDate = LocalDateTime.now().plusDays(1); // 结束时间
        // 遍历菜谱ID，加入到用户的今日计划中
        createReqVO.getRecipeIds().forEach(recipeId -> {
            // 判断是否已经存在
            if (dailyPlanMapper.selectList(new LambdaQueryWrapperX<DailyPlanDO>()
                    .eq(DailyPlanDO::getUserId, loginUserId) // 该用户
                    .ge(DailyPlanDO::getPlanDate, startDate)  // 大于等于当天开始时间
                    .lt(DailyPlanDO::getPlanDate, endDate))    // 小于第二天开始时间
                    .isEmpty()) {
                DailyPlanDO dailyPlan = BeanUtils.toBean(createReqVO, DailyPlanDO.class);
                dailyPlan.setUserId(loginUserId); // 用户
                dailyPlan.setPlanDate(startDate); // 日期
                dailyPlanMapper.insert(dailyPlan); // 插入
                planIds.add(dailyPlan.getId()); // 将生成的id装入集合中进行返回
            } else {
                throw exception(DAILY_PLAN_ALREADY_EXISTS);
            }
        });
        return planIds;
    }
}
