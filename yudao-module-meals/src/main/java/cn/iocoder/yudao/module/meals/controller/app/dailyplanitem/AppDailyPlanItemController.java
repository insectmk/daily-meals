package cn.iocoder.yudao.module.meals.controller.app.dailyplanitem;

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

import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.DailyPlanItemDO;
import cn.iocoder.yudao.module.meals.service.dailyplanitem.DailyPlanItemService;

@Tag(name = "用户 APP - 每日计划明细")
@RestController
@RequestMapping("/meals/daily-plan-item")
@Validated
public class AppDailyPlanItemController {

    @Resource
    private DailyPlanItemService dailyPlanItemService;

    @PostMapping("/create")
    @Operation(summary = "创建每日计划明细")
    public CommonResult<Long> createDailyPlanItem(@Valid @RequestBody AppDailyPlanItemSaveReqVO createReqVO) {
        return success(dailyPlanItemService.createDailyPlanItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新每日计划明细")
    public CommonResult<Boolean> updateDailyPlanItem(@Valid @RequestBody AppDailyPlanItemSaveReqVO updateReqVO) {
        dailyPlanItemService.updateDailyPlanItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除每日计划明细")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteDailyPlanItem(@RequestParam("id") Long id) {
        dailyPlanItemService.deleteDailyPlanItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得每日计划明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppDailyPlanItemRespVO> getDailyPlanItem(@RequestParam("id") Long id) {
        DailyPlanItemDO dailyPlanItem = dailyPlanItemService.getDailyPlanItem(id);
        return success(BeanUtils.toBean(dailyPlanItem, AppDailyPlanItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得每日计划明细分页")
    public CommonResult<PageResult<AppDailyPlanItemRespVO>> getDailyPlanItemPage(@Valid AppDailyPlanItemPageReqVO pageReqVO) {
        PageResult<DailyPlanItemDO> pageResult = dailyPlanItemService.getDailyPlanItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppDailyPlanItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出每日计划明细 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDailyPlanItemExcel(@Valid AppDailyPlanItemPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DailyPlanItemDO> list = dailyPlanItemService.getDailyPlanItemPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "每日计划明细.xls", "数据", AppDailyPlanItemRespVO.class,
                        BeanUtils.toBean(list, AppDailyPlanItemRespVO.class));
    }

}