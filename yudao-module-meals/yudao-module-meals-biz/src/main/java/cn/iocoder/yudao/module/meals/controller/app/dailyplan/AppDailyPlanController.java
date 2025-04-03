package cn.iocoder.yudao.module.meals.controller.app.dailyplan;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanRecipeSaveTodayReqVO;
import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import cn.iocoder.yudao.module.meals.service.dailyplan.DailyPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

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

    @GetMapping("/page")
    @Operation(summary = "分页查询菜谱计划")
    public CommonResult<PageResult<AppDailyPlanRespVO>> getDailyPlanPage(@Valid AppDailyPlanPageReqVO pageReqVO) {
        PageResult<DailyPlanDO> pageResult = dailyPlanService.getDailyPlanPage(pageReqVO, getLoginUserId());
        return success(BeanUtils.toBean(pageResult, AppDailyPlanRespVO.class));
    }
}
