package cn.iocoder.yudao.module.meals.controller.app.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.food.vo.FoodSimpleRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeSaveReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSimpleRespVO;
import cn.iocoder.yudao.module.meals.convert.food.FoodConvert;
import cn.iocoder.yudao.module.meals.convert.recipemenu.RecipeMenuConvert;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.module.meals.enums.RecipeStatusEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import cn.iocoder.yudao.module.meals.service.recipemenu.AppRecipeMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 菜谱菜单")
@RestController
@RequestMapping("/meals/recipe-menu")
@Validated
public class AppRecipeMenuController {

    @Resource
    private AppRecipeMenuService appRecipeMenuService;

    @PostMapping("/create-or-update")
    @Operation(summary = "创建或更新菜谱菜单")
    public CommonResult<Long> createOrUpdateRecipe(@Valid @RequestBody AppRecipeMenuSaveReqVO createReqVO) {
        return success(appRecipeMenuService.createOrUpdateRecipe(getLoginUserId(), createReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "创建菜谱菜单")
    public CommonResult<Long> createRecipeMenu(@Valid @RequestBody AppRecipeMenuSaveReqVO createReqVO) {
        createReqVO.setUserId(getLoginUserId()); // 用户ID
        createReqVO.setMenuType(RecipeTypesEnum.USER.getType()); // 菜单类型，用户
        return success(appRecipeMenuService.createRecipeMenu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜谱菜单")
    public CommonResult<Boolean> updateRecipeMenu(@Valid @RequestBody AppRecipeMenuSaveReqVO updateReqVO) {
        appRecipeMenuService.updateRecipeMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜谱菜单")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteRecipeMenu(@RequestParam("id") Long id) {
        appRecipeMenuService.deleteRecipeMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜谱菜单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppRecipeMenuRespVO> getRecipeMenu(@RequestParam("id") Long id) {
        AppRecipeMenuRespVO recipeMenu = appRecipeMenuService.getRecipeMenuDetail(getLoginUserId(), id);
        return success(recipeMenu);
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户可见菜谱菜单分页")
    @PermitAll
    public CommonResult<PageResult<AppRecipeMenuRespVO>> getRecipeMenuPage(@Valid AppRecipeMenuPageReqVO pageReqVO) {
        PageResult<RecipeMenuDO> pageResult = appRecipeMenuService.getUserViewableRecipeMenuPage(getLoginUserId(), pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppRecipeMenuRespVO.class));
    }

    @GetMapping("/page-self")
    @Operation(summary = "获得用户可见菜谱菜单分页")
    @PermitAll
    public CommonResult<PageResult<AppRecipeMenuRespVO>> getSelfRecipeMenuPage(@Valid AppRecipeMenuPageReqVO pageReqVO) {
        PageResult<RecipeMenuDO> pageResult = appRecipeMenuService.getUserSelfRecipeMenuPage(getLoginUserId(), pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppRecipeMenuRespVO.class));
    }

    @GetMapping("/list-self-simple")
    @Operation(summary = "获取自己的菜单精简信息列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<AppRecipeMenuSimpleRespVO>> getSelfRecipeMenuList() {
        List<RecipeMenuDO> list = appRecipeMenuService.getSelfRecipeMenuList(getLoginUserId());
        return success(RecipeMenuConvert.INSTANCE.convertSimpleList(list));
    }
}
