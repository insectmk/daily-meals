package cn.iocoder.yudao.module.meals.controller.admin.foodcategory;

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

import cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.foodcategory.FoodCategoryDO;
import cn.iocoder.yudao.module.meals.service.foodcategory.FoodCategoryService;

@Tag(name = "管理后台 - 食材分类")
@RestController
@RequestMapping("/meals/food-category")
@Validated
public class FoodCategoryController {

    @Resource
    private FoodCategoryService foodCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建食材分类")
    @PreAuthorize("@ss.hasPermission('meals:food-category:create')")
    public CommonResult<Long> createFoodCategory(@Valid @RequestBody FoodCategorySaveReqVO createReqVO) {
        return success(foodCategoryService.createFoodCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新食材分类")
    @PreAuthorize("@ss.hasPermission('meals:food-category:update')")
    public CommonResult<Boolean> updateFoodCategory(@Valid @RequestBody FoodCategorySaveReqVO updateReqVO) {
        foodCategoryService.updateFoodCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除食材分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:food-category:delete')")
    public CommonResult<Boolean> deleteFoodCategory(@RequestParam("id") Long id) {
        foodCategoryService.deleteFoodCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得食材分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:food-category:query')")
    public CommonResult<FoodCategoryRespVO> getFoodCategory(@RequestParam("id") Long id) {
        FoodCategoryDO foodCategory = foodCategoryService.getFoodCategory(id);
        return success(BeanUtils.toBean(foodCategory, FoodCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得食材分类列表")
    @PreAuthorize("@ss.hasPermission('meals:food-category:query')")
    public CommonResult<List<FoodCategoryRespVO>> getFoodCategoryList(@Valid FoodCategoryListReqVO listReqVO) {
        List<FoodCategoryDO> list = foodCategoryService.getFoodCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, FoodCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出食材分类 Excel")
    @PreAuthorize("@ss.hasPermission('meals:food-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFoodCategoryExcel(@Valid FoodCategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<FoodCategoryDO> list = foodCategoryService.getFoodCategoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "食材分类.xls", "数据", FoodCategoryRespVO.class,
                        BeanUtils.toBean(list, FoodCategoryRespVO.class));
    }

}