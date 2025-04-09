package cn.iocoder.yudao.module.meals.service.dailyplan;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.AppDailyPlanItemDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeFoodDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.convert.dailyplan.DailyPlanConvert;
import cn.iocoder.yudao.module.meals.convert.recipe.RecipeConvert;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDetailDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDetailDO;
import cn.iocoder.yudao.module.meals.dal.mysql.dailyplanitem.DailyPlanItemMapper;
import cn.iocoder.yudao.module.meals.service.recipe.AppRecipeService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
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
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
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
    @Resource
    private DailyPlanItemMapper dailyPlanItemMapper;
    @Resource
    private AppRecipeService appRecipeService;

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
    public Long addRecipesToPlan(AppDailyPlanRecipeSaveTodayReqVO createReqVO, Long loginUserId) {
        LocalDate planDate = createReqVO.getPlanDate().toLocalDate(); // 计划日期
        LocalDateTime startDate = planDate.atStartOfDay(); // 开始时间
        LocalDateTime endDate = startDate.plusDays(1); // 结束时间
        // 1. 判断是否存在指定日期与类型的菜谱
        DailyPlanDO dailyPlanDO = dailyPlanMapper.selectOne(new LambdaQueryWrapperX<DailyPlanDO>()
                .eq(DailyPlanDO::getUserId, loginUserId) // 该用户
                .ge(DailyPlanDO::getPlanDate, startDate)  // 大于等于当天开始时间
                .lt(DailyPlanDO::getPlanDate, endDate));// 小于第二天开始时间
        long planId; // 计划ID
        if (ObjUtil.isEmpty(dailyPlanDO)) {
            // 1.1 不存在计划，创建计划
            dailyPlanDO = BeanUtils.toBean(createReqVO, DailyPlanDO.class);
            dailyPlanDO.setUserId(loginUserId); // 用户
            dailyPlanDO.setPlanDate(startDate); // 日期
            dailyPlanMapper.insert(dailyPlanDO);
            planId = dailyPlanDO.getId();
        } else {
            planId = dailyPlanDO.getId();
            // 1.2 存在计划，判断菜谱是否在计划中
            if (!dailyPlanItemMapper.selectList(new LambdaQueryWrapperX<DailyPlanItemDO>()
                            .eq(DailyPlanItemDO::getPlanId, planId) // 该计划
                            .eq(DailyPlanItemDO::getMealType, createReqVO.getMealType()) // 该类型
                            .in(DailyPlanItemDO::getRecipeId, createReqVO.getRecipeIds()))  // 菜谱
                    .isEmpty()) {
                throw exception(DAILY_PLAN_ALREADY_EXISTS);
            }
        }
        // 2 遍历菜谱ID，加入到用户的今日计划明细中
        createReqVO.getRecipeIds().forEach(recipeId -> {
            DailyPlanItemDO dailyPlanItemDO = new DailyPlanItemDO();
            dailyPlanItemDO.setPlanId(planId); // 计划编码
            dailyPlanItemDO.setRecipeId(recipeId); // 计划编码
            dailyPlanItemDO.setMealType(createReqVO.getMealType()); // 计划类型
            dailyPlanItemMapper.insert(dailyPlanItemDO); // 插入
        });
        return planId;
    }

    @Override
    public PageResult<AppDailyPlanDetailRespVO> getDailyPlanDetailPage(AppDailyPlanPageReqVO pageReqVO, Long userId) {
        // 查询基础信息
        PageResult<DailyPlanDO> pageResult = dailyPlanMapper.selectPage(pageReqVO, userId);
        List<DailyPlanDO> plans = pageResult.getList(); // 计划信息
        if (CollUtil.isEmpty(plans)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppDailyPlanDetailRespVO.class);
        }
        // 查询计划详情信息
        List<DailyPlanItemDetailDO> planItems = dailyPlanItemMapper.selectJoinList(
                DailyPlanItemDetailDO.class,
                new MPJLambdaWrapper<DailyPlanItemDO>()
                        .selectAll(DailyPlanItemDO.class) // 查询所有基础字段
                        .selectAs(RecipeDO::getName, DailyPlanItemDetailDO::getRecipeName) // 食谱的名称作为详细信息名称
                        .leftJoin(RecipeDO.class, RecipeDO::getId, DailyPlanItemDetailDO::getRecipeId) // 联表 WHERE meals_recipe.id = meals_daily_plan_item.recipe_id
                        // 查询所有分页菜谱的食材信息
                        .in(DailyPlanItemDetailDO::getPlanId, convertSet(plans, DailyPlanDO::getId))
                        .orderByDesc(DailyPlanItemDetailDO::getCreateTime));
        // 装载信息
        return DailyPlanConvert.INSTANCE.convertPage(pageResult, planItems);
    }

    @Override
    public AppDailyPlanDetailRespVO getDailyPlanDetail(Long id) {
        // 查询基础信息
        DailyPlanDO dailyPlanDO = dailyPlanMapper.selectById(id);
        AppDailyPlanDetailRespVO detailRespVO = BeanUtils.toBean(dailyPlanDO, AppDailyPlanDetailRespVO.class);
        // 查询计划明细信息
        List<DailyPlanItemDetailDO> planItems = dailyPlanItemMapper.selectJoinList(
                DailyPlanItemDetailDO.class,
                new MPJLambdaWrapper<DailyPlanItemDO>()
                        .selectAll(DailyPlanItemDO.class) // 查询所有基础字段
                        .selectAs(RecipeDO::getName, DailyPlanItemDetailDO::getRecipeName) // 食谱的名称作为详细信息名称
                        .leftJoin(RecipeDO.class, RecipeDO::getId, DailyPlanItemDetailDO::getRecipeId) // 联表 WHERE meals_recipe.id = meals_daily_plan_item.recipe_id
                        .eq(DailyPlanItemDO::getPlanId, id) // 计划ID
                        .orderByDesc(DailyPlanItemDetailDO::getCreateTime));
        // 转化itemDO为VO
        List<AppDailyPlanItemDetailRespVO> planItemVOs = BeanUtils.toBean(planItems, AppDailyPlanItemDetailRespVO.class);
        // 查询菜谱信息装载到计划明细中
        planItemVOs.forEach(planItemVO -> {
            planItemVO.setRecipeInfo(appRecipeService.getRecipeDetail(planItemVO.getRecipeId()));
        });
        // 拼装信息
        detailRespVO.setItems(planItemVOs);
        return detailRespVO;
    }
}
