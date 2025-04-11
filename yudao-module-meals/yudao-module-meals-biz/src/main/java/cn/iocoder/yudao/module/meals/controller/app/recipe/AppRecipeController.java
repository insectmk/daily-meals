package cn.iocoder.yudao.module.meals.controller.app.recipe;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePopularPublicReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeSaveReqVO;
import cn.iocoder.yudao.module.meals.service.recipe.AppRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @PermitAll
    public CommonResult<PageResult<AppRecipeRespVO>> getPublicRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getPublicRecipeDetailPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/public/popular")
    @Operation(summary = "获得最热门的公共菜谱")
    @PermitAll
    public CommonResult<List<AppRecipeRespVO>> getPopularPublicRecipes(@Valid AppRecipePopularPublicReqVO reqVO) {
        List<AppRecipeRespVO> result = appRecipeService.getPopularPublicRecipesDetail(reqVO);
        return success(result);
    }

    @PostMapping("/create")
    @Operation(summary = "创建菜谱")
    public CommonResult<Long> createRecipe(@Valid @RequestBody AppRecipeSaveReqVO createReqVO) {
        createReqVO.setUserId(getLoginUserId()); // 设置用户ID
        createReqVO.setStatus(0); // 设置状态
        return success(appRecipeService.createRecipe(createReqVO));
    }
}
