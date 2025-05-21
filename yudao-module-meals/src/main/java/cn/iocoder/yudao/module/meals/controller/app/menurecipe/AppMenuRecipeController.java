package cn.iocoder.yudao.module.meals.controller.app.menurecipe;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.menurecipe.vo.AppMenuRecipeRespVO;
import cn.iocoder.yudao.module.meals.controller.app.menurecipe.vo.AppMenuRecipeSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.module.meals.service.menurecipe.AppMenuRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户AP - 菜单菜谱")
@RestController
@RequestMapping("/meals/menu-recipe")
@Validated
public class AppMenuRecipeController {

    @Resource
    private AppMenuRecipeService appMenuRecipeService;

    @PostMapping("/create")
    @Operation(summary = "创建菜单菜谱")
    public CommonResult<Long> createMenuRecipe(@Valid @RequestBody AppMenuRecipeSaveReqVO createReqVO) {
        return success(appMenuRecipeService.createMenuRecipe(createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单菜谱")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteMenuRecipe(@RequestParam("id") Long id) {
        appMenuRecipeService.deleteMenuRecipe(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜单菜谱")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppMenuRecipeRespVO> getMenuRecipe(@RequestParam("id") Long id) {
        MenuRecipeDO menuRecipe = appMenuRecipeService.getMenuRecipe(id);
        return success(BeanUtils.toBean(menuRecipe, AppMenuRecipeRespVO.class));
    }

}
