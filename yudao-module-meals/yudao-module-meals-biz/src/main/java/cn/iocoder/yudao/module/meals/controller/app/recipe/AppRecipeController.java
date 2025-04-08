package cn.iocoder.yudao.module.meals.controller.app.recipe;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipeRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.service.recipe.AppRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 菜谱")
@RestController
@RequestMapping("/meals/recipe")
@Validated
public class AppRecipeController {

    @Resource
    private AppRecipeService appRecipeService;

    @GetMapping("/get")
    @Operation(summary = "获取菜谱")
    public CommonResult<AppRecipeRespVO> createRecipe(@RequestParam("id") Long id) {
        AppRecipeRespVO recipe = appRecipeService.getRecipeDetail(id);
        return success(recipe);
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜谱分页")
    public CommonResult<PageResult<AppRecipeRespVO>> getRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getRecipeDetailPage(getLoginUserId(),pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/page/public")
    @Operation(summary = "获得公共菜谱分页")
    public CommonResult<PageResult<AppRecipeRespVO>> getPublicRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getPublicRecipeDetailPage(pageReqVO);
        return success(pageResult);
    }
}
