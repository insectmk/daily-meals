package cn.iocoder.yudao.module.meals.controller.admin.menurecipe;

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

import cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.module.meals.service.menurecipe.MenuRecipeService;

@Tag(name = "管理后台 - 菜单菜谱")
@RestController
@RequestMapping("/meals/menu-recipe")
@Validated
public class MenuRecipeController {

    @Resource
    private MenuRecipeService menuRecipeService;

    @PostMapping("/create")
    @Operation(summary = "创建菜单菜谱")
    @PreAuthorize("@ss.hasPermission('meals:menu-recipe:create')")
    public CommonResult<Long> createMenuRecipe(@Valid @RequestBody MenuRecipeSaveReqVO createReqVO) {
        return success(menuRecipeService.createMenuRecipe(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜单菜谱")
    @PreAuthorize("@ss.hasPermission('meals:menu-recipe:update')")
    public CommonResult<Boolean> updateMenuRecipe(@Valid @RequestBody MenuRecipeSaveReqVO updateReqVO) {
        menuRecipeService.updateMenuRecipe(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单菜谱")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:menu-recipe:delete')")
    public CommonResult<Boolean> deleteMenuRecipe(@RequestParam("id") Long id) {
        menuRecipeService.deleteMenuRecipe(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜单菜谱")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:menu-recipe:query')")
    public CommonResult<MenuRecipeRespVO> getMenuRecipe(@RequestParam("id") Long id) {
        MenuRecipeDO menuRecipe = menuRecipeService.getMenuRecipe(id);
        return success(BeanUtils.toBean(menuRecipe, MenuRecipeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜单菜谱分页")
    @PreAuthorize("@ss.hasPermission('meals:menu-recipe:query')")
    public CommonResult<PageResult<MenuRecipeRespVO>> getMenuRecipePage(@Valid MenuRecipePageReqVO pageReqVO) {
        PageResult<MenuRecipeDO> pageResult = menuRecipeService.getMenuRecipePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MenuRecipeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菜单菜谱 Excel")
    @PreAuthorize("@ss.hasPermission('meals:menu-recipe:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMenuRecipeExcel(@Valid MenuRecipePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MenuRecipeDO> list = menuRecipeService.getMenuRecipePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "菜单菜谱.xls", "数据", MenuRecipeRespVO.class,
                        BeanUtils.toBean(list, MenuRecipeRespVO.class));
    }

}