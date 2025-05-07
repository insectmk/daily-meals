package cn.iocoder.yudao.module.meals.controller.app.menu;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.menu.vo.AppMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.menu.vo.AppMenuRespVO;
import cn.iocoder.yudao.module.meals.controller.app.menu.vo.AppMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menu.MenuDO;
import cn.iocoder.yudao.module.meals.service.menu.AppMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 菜单")
@RestController
@RequestMapping("/meals/menu")
@Validated
public class AppMenuController {

    @Resource
    private AppMenuService appMenuService;

    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    public CommonResult<Long> createMenu(@Valid @RequestBody AppMenuSaveReqVO createReqVO) {
        return success(appMenuService.createMenu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜单")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody AppMenuSaveReqVO updateReqVO) {
        appMenuService.updateMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        appMenuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppMenuRespVO> getMenu(@RequestParam("id") Long id) {
        MenuDO menu = appMenuService.getMenu(id);
        return success(BeanUtils.toBean(menu, AppMenuRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户可见菜单分页")
    public CommonResult<PageResult<AppMenuRespVO>> getMenuPage(@Valid AppMenuPageReqVO pageReqVO) {
        PageResult<MenuDO> pageResult = appMenuService.getMenuPage(getLoginUserId(), pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppMenuRespVO.class));
    }

}
