package cn.iocoder.yudao.module.meals.controller.app.dailyplan;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanRecipeSaveTodayReqVO;
import cn.iocoder.yudao.module.meals.service.dailyplan.DailyPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 菜谱计划")
@RestController
@RequestMapping("/meals/daily-plan")
@Validated
public class AppDailyPlanController {

    @Resource
    private DailyPlanService dailyPlanService;

    @PostMapping("/create")
    @Operation(summary = "将菜谱加入到计划")
    public CommonResult<Long> addRecipesTodayPlan(@Valid @RequestBody AppDailyPlanRecipeSaveTodayReqVO createReqVO) {
        Long planId = dailyPlanService.addRecipesToPlan(createReqVO, getLoginUserId());
        return success(planId);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询菜谱计划")
    public CommonResult<PageResult<AppDailyPlanDetailRespVO>> getDailyPlanPage(@Valid AppDailyPlanPageReqVO pageReqVO) {
        PageResult<AppDailyPlanDetailRespVO> pageResult = dailyPlanService.getDailyPlanDetailPage(pageReqVO, getLoginUserId());
        return success(pageResult);
    }

    @GetMapping("/get")
    @Operation(summary = "获取计划")
    public CommonResult<AppDailyPlanDetailRespVO> createRecipe(@RequestParam("id") Long id) {
        AppDailyPlanDetailRespVO plan = dailyPlanService.getDailyPlanDetail(id);
        return success(plan);
    }
}
