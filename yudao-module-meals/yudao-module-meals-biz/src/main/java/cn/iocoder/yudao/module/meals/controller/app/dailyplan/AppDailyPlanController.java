package cn.iocoder.yudao.module.meals.controller.app.dailyplan;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.*;
import cn.iocoder.yudao.module.meals.service.dailyplan.DailyPlanService;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 菜谱计划")
@RestController
@RequestMapping("/meals/daily-plan")
@Validated
public class AppDailyPlanController {

    @Resource
    private DailyPlanService dailyPlanService;

    @PostMapping("/create/today")
    @Operation(summary = "将菜谱加入到今日计划")
    public CommonResult<List<Long>> addRecipesTodayPlan(@Valid @RequestBody AppDailyPlanRecipeSaveTodayReqVO createReqVO) {
        List<Long> planIds = dailyPlanService.addRecipesTodayPlan(createReqVO, getLoginUserId());
        return success(planIds);
    }
}
