package cn.iocoder.yudao.module.meals.controller.app.recipe;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.service.recipe.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 菜谱")
@RestController
@RequestMapping("/meals/recipe")
@Validated
public class AppRecipeController {

    @Resource
    private RecipeService recipeService;

    @PostMapping("/get")
    @Operation(summary = "获取菜谱")
    public CommonResult<RecipeDO> createRecipe(@RequestParam("id") Long id) {
        RecipeDO recipe = recipeService.getRecipe(id);
        return success(BeanUtils.toBean(recipe, RecipeDO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜谱分页")
    public CommonResult<PageResult<AppRecipeRespVO>> getDailyPlanPage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<RecipeDO> pageResult = recipeService.getRecipePage(getLoginUserId(),pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppRecipeRespVO.class));
    }
}
