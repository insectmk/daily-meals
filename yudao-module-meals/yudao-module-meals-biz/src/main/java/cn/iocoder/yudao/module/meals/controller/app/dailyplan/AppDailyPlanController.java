package cn.iocoder.yudao.module.meals.controller.app.dailyplan;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import cn.iocoder.yudao.module.meals.service.dailyplan.DailyPlanService;

@Tag(name = "用户 APP - 菜谱计划")
@RestController
@RequestMapping("/meals/daily-plan")
@Validated
public class AppDailyPlanController {

    @Resource
    private DailyPlanService dailyPlanService;

    @PostMapping("/create")
    @Operation(summary = "创建菜谱计划")
    public CommonResult<Long> createDailyPlan(@Valid @RequestBody AppDailyPlanSaveReqVO createReqVO) {
        return success(dailyPlanService.createDailyPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜谱计划")
    public CommonResult<Boolean> updateDailyPlan(@Valid @RequestBody AppDailyPlanSaveReqVO updateReqVO) {
        dailyPlanService.updateDailyPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜谱计划")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteDailyPlan(@RequestParam("id") Long id) {
        dailyPlanService.deleteDailyPlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜谱计划")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppDailyPlanRespVO> getDailyPlan(@RequestParam("id") Long id) {
        DailyPlanDO dailyPlan = dailyPlanService.getDailyPlan(id);
        return success(BeanUtils.toBean(dailyPlan, AppDailyPlanRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜谱计划分页")
    public CommonResult<PageResult<AppDailyPlanRespVO>> getDailyPlanPage(@Valid AppDailyPlanPageReqVO pageReqVO) {
        PageResult<DailyPlanDO> pageResult = dailyPlanService.getDailyPlanPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppDailyPlanRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菜谱计划 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDailyPlanExcel(@Valid AppDailyPlanPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DailyPlanDO> list = dailyPlanService.getDailyPlanPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "菜谱计划.xls", "数据", AppDailyPlanRespVO.class,
                        BeanUtils.toBean(list, AppDailyPlanRespVO.class));
    }

}