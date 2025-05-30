package cn.iocoder.yudao.module.meals.controller.admin.recipecategory;

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

import cn.iocoder.yudao.module.meals.controller.admin.recipecategory.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipecategory.RecipeCategoryDO;
import cn.iocoder.yudao.module.meals.service.recipecategory.RecipeCategoryService;

@Tag(name = "管理后台 - 菜谱分类")
@RestController
@RequestMapping("/meals/recipe-category")
@Validated
public class RecipeCategoryController {

    @Resource
    private RecipeCategoryService recipeCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建菜谱分类")
    @PreAuthorize("@ss.hasPermission('meals:recipe-category:create')")
    public CommonResult<Long> createRecipeCategory(@Valid @RequestBody RecipeCategorySaveReqVO createReqVO) {
        return success(recipeCategoryService.createRecipeCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜谱分类")
    @PreAuthorize("@ss.hasPermission('meals:recipe-category:update')")
    public CommonResult<Boolean> updateRecipeCategory(@Valid @RequestBody RecipeCategorySaveReqVO updateReqVO) {
        recipeCategoryService.updateRecipeCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜谱分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:recipe-category:delete')")
    public CommonResult<Boolean> deleteRecipeCategory(@RequestParam("id") Long id) {
        recipeCategoryService.deleteRecipeCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜谱分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:recipe-category:query')")
    public CommonResult<RecipeCategoryRespVO> getRecipeCategory(@RequestParam("id") Long id) {
        RecipeCategoryDO recipeCategory = recipeCategoryService.getRecipeCategory(id);
        return success(BeanUtils.toBean(recipeCategory, RecipeCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得菜谱分类列表")
    @PreAuthorize("@ss.hasPermission('meals:recipe-category:query')")
    public CommonResult<List<RecipeCategoryRespVO>> getRecipeCategoryList(@Valid RecipeCategoryListReqVO listReqVO) {
        List<RecipeCategoryDO> list = recipeCategoryService.getRecipeCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, RecipeCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菜谱分类 Excel")
    @PreAuthorize("@ss.hasPermission('meals:recipe-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRecipeCategoryExcel(@Valid RecipeCategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<RecipeCategoryDO> list = recipeCategoryService.getRecipeCategoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "菜谱分类.xls", "数据", RecipeCategoryRespVO.class,
                        BeanUtils.toBean(list, RecipeCategoryRespVO.class));
    }

}