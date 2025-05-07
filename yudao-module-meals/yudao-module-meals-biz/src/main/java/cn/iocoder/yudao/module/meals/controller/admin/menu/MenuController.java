package cn.iocoder.yudao.module.meals.controller.admin.menu;

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

import cn.iocoder.yudao.module.meals.controller.admin.menu.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.menu.MenuDO;
import cn.iocoder.yudao.module.meals.service.menu.MenuService;

@Tag(name = "管理后台 - 菜单")
@RestController
@RequestMapping("/meals/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    @PreAuthorize("@ss.hasPermission('meals:menu:create')")
    public CommonResult<Long> createMenu(@Valid @RequestBody MenuSaveReqVO createReqVO) {
        return success(menuService.createMenu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜单")
    @PreAuthorize("@ss.hasPermission('meals:menu:update')")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuSaveReqVO updateReqVO) {
        menuService.updateMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:menu:query')")
    public CommonResult<MenuRespVO> getMenu(@RequestParam("id") Long id) {
        MenuDO menu = menuService.getMenu(id);
        return success(BeanUtils.toBean(menu, MenuRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜单分页")
    @PreAuthorize("@ss.hasPermission('meals:menu:query')")
    public CommonResult<PageResult<MenuRespVO>> getMenuPage(@Valid MenuPageReqVO pageReqVO) {
        PageResult<MenuDO> pageResult = menuService.getMenuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MenuRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菜单 Excel")
    @PreAuthorize("@ss.hasPermission('meals:menu:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMenuExcel(@Valid MenuPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MenuDO> list = menuService.getMenuPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "菜单.xls", "数据", MenuRespVO.class,
                        BeanUtils.toBean(list, MenuRespVO.class));
    }

}