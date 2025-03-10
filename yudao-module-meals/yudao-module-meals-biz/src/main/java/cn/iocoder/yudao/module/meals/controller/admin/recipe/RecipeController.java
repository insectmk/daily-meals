package cn.iocoder.yudao.module.meals.controller.admin.recipe;

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

import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.service.recipe.RecipeService;

@Tag(name = "管理后台 - 菜谱")
@RestController
@RequestMapping("/meals/recipe")
@Validated
public class RecipeController {

    @Resource
    private RecipeService recipeService;

    @PostMapping("/create")
    @Operation(summary = "创建菜谱")
    @PreAuthorize("@ss.hasPermission('meals:recipe:create')")
    public CommonResult<Long> createRecipe(@Valid @RequestBody RecipeSaveReqVO createReqVO) {
        return success(recipeService.createRecipe(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜谱")
    @PreAuthorize("@ss.hasPermission('meals:recipe:update')")
    public CommonResult<Boolean> updateRecipe(@Valid @RequestBody RecipeSaveReqVO updateReqVO) {
        recipeService.updateRecipe(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜谱")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:recipe:delete')")
    public CommonResult<Boolean> deleteRecipe(@RequestParam("id") Long id) {
        recipeService.deleteRecipe(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜谱")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:recipe:query')")
    public CommonResult<RecipeRespVO> getRecipe(@RequestParam("id") Long id) {
        RecipeDO recipe = recipeService.getRecipe(id);
        return success(BeanUtils.toBean(recipe, RecipeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜谱分页")
    @PreAuthorize("@ss.hasPermission('meals:recipe:query')")
    public CommonResult<PageResult<RecipeRespVO>> getRecipePage(@Valid RecipePageReqVO pageReqVO) {
        PageResult<RecipeDO> pageResult = recipeService.getRecipePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RecipeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菜谱 Excel")
    @PreAuthorize("@ss.hasPermission('meals:recipe:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRecipeExcel(@Valid RecipePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RecipeDO> list = recipeService.getRecipePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "菜谱.xls", "数据", RecipeRespVO.class,
                        BeanUtils.toBean(list, RecipeRespVO.class));
    }

    // ==================== 子表（菜谱食材） ====================

    @GetMapping("/recipe-food/page")
    @Operation(summary = "获得菜谱食材分页")
    @Parameter(name = "recipeId", description = "菜谱ID")
    @PreAuthorize("@ss.hasPermission('meals:recipe:query')")
    public CommonResult<PageResult<RecipeFoodDO>> getRecipeFoodPage(PageParam pageReqVO,
                                                                                        @RequestParam("recipeId") Long recipeId) {
        return success(recipeService.getRecipeFoodPage(pageReqVO, recipeId));
    }

    @PostMapping("/recipe-food/create")
    @Operation(summary = "创建菜谱食材")
    @PreAuthorize("@ss.hasPermission('meals:recipe:create')")
    public CommonResult<Long> createRecipeFood(@Valid @RequestBody RecipeFoodDO recipeFood) {
        return success(recipeService.createRecipeFood(recipeFood));
    }

    @PutMapping("/recipe-food/update")
    @Operation(summary = "更新菜谱食材")
    @PreAuthorize("@ss.hasPermission('meals:recipe:update')")
    public CommonResult<Boolean> updateRecipeFood(@Valid @RequestBody RecipeFoodDO recipeFood) {
        recipeService.updateRecipeFood(recipeFood);
        return success(true);
    }

    @DeleteMapping("/recipe-food/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除菜谱食材")
    @PreAuthorize("@ss.hasPermission('meals:recipe:delete')")
    public CommonResult<Boolean> deleteRecipeFood(@RequestParam("id") Long id) {
        recipeService.deleteRecipeFood(id);
        return success(true);
    }

	@GetMapping("/recipe-food/get")
	@Operation(summary = "获得菜谱食材")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:recipe:query')")
	public CommonResult<RecipeFoodDO> getRecipeFood(@RequestParam("id") Long id) {
	    return success(recipeService.getRecipeFood(id));
	}

}