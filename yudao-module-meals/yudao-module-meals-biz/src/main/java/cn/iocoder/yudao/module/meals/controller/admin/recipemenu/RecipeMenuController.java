package cn.iocoder.yudao.module.meals.controller.admin.recipemenu;

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

import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.module.meals.service.recipemenu.RecipeMenuService;

@Tag(name = "管理后台 - 菜谱菜单")
@RestController
@RequestMapping("/meals/recipe-menu")
@Validated
public class RecipeMenuController {

    @Resource
    private RecipeMenuService recipeMenuService;

    @PostMapping("/create")
    @Operation(summary = "创建菜谱菜单")
    @PreAuthorize("@ss.hasPermission('meals:recipe-menu:create')")
    public CommonResult<Long> createRecipeMenu(@Valid @RequestBody RecipeMenuSaveReqVO createReqVO) {
        return success(recipeMenuService.createRecipeMenu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜谱菜单")
    @PreAuthorize("@ss.hasPermission('meals:recipe-menu:update')")
    public CommonResult<Boolean> updateRecipeMenu(@Valid @RequestBody RecipeMenuSaveReqVO updateReqVO) {
        recipeMenuService.updateRecipeMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜谱菜单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:recipe-menu:delete')")
    public CommonResult<Boolean> deleteRecipeMenu(@RequestParam("id") Long id) {
        recipeMenuService.deleteRecipeMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜谱菜单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:recipe-menu:query')")
    public CommonResult<RecipeMenuRespVO> getRecipeMenu(@RequestParam("id") Long id) {
        RecipeMenuDO recipeMenu = recipeMenuService.getRecipeMenu(id);
        return success(BeanUtils.toBean(recipeMenu, RecipeMenuRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜谱菜单分页")
    @PreAuthorize("@ss.hasPermission('meals:recipe-menu:query')")
    public CommonResult<PageResult<RecipeMenuRespVO>> getRecipeMenuPage(@Valid RecipeMenuPageReqVO pageReqVO) {
        PageResult<RecipeMenuDO> pageResult = recipeMenuService.getRecipeMenuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RecipeMenuRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菜谱菜单 Excel")
    @PreAuthorize("@ss.hasPermission('meals:recipe-menu:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRecipeMenuExcel(@Valid RecipeMenuPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RecipeMenuDO> list = recipeMenuService.getRecipeMenuPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "菜谱菜单.xls", "数据", RecipeMenuRespVO.class,
                        BeanUtils.toBean(list, RecipeMenuRespVO.class));
    }

}