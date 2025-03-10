package cn.iocoder.yudao.module.meals.controller.admin.food;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
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

import cn.iocoder.yudao.module.meals.controller.admin.food.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.service.food.FoodService;

@Tag(name = "管理后台 - 食材")
@RestController
@RequestMapping("/meals/food")
@Validated
public class FoodController {

    @Resource
    private FoodService foodService;

    @PostMapping("/create")
    @Operation(summary = "创建食材")
    @PreAuthorize("@ss.hasPermission('meals:food:create')")
    public CommonResult<Long> createFood(@Valid @RequestBody FoodSaveReqVO createReqVO) {
        return success(foodService.createFood(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新食材")
    @PreAuthorize("@ss.hasPermission('meals:food:update')")
    public CommonResult<Boolean> updateFood(@Valid @RequestBody FoodSaveReqVO updateReqVO) {
        foodService.updateFood(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除食材")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:food:delete')")
    public CommonResult<Boolean> deleteFood(@RequestParam("id") Long id) {
        foodService.deleteFood(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得食材")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:food:query')")
    public CommonResult<FoodRespVO> getFood(@RequestParam("id") Long id) {
        FoodDO food = foodService.getFood(id);
        return success(BeanUtils.toBean(food, FoodRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得食材分页")
    @PreAuthorize("@ss.hasPermission('meals:food:query')")
    public CommonResult<PageResult<FoodRespVO>> getFoodPage(@Valid FoodPageReqVO pageReqVO) {
        PageResult<FoodDO> pageResult = foodService.getFoodPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FoodRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出食材 Excel")
    @PreAuthorize("@ss.hasPermission('meals:food:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFoodExcel(@Valid FoodPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FoodDO> list = foodService.getFoodPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "食材.xls", "数据", FoodRespVO.class,
                        BeanUtils.toBean(list, FoodRespVO.class));
    }

}