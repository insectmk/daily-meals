package cn.iocoder.yudao.module.meals.controller.app.recipe;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.*;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentSaveReqVO;
import cn.iocoder.yudao.module.meals.service.recipe.AppRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/common")
    @Operation(summary = "评论菜谱")
    public CommonResult<Long> createComment(@Valid @RequestBody AppRecipeCommentSaveReqVO createReqVO) {
        return success(appRecipeService.createRecipeComment(getLoginUserId(),createReqVO));
    }

    @GetMapping("/page-favor-users")
    @Operation(summary = "获取用户关注作者的的菜谱分页")
    public CommonResult<PageResult<AppRecipeRespVO>> getFavorUsersRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getFavorUsersRecipePage(getLoginUserId(),pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/page-self-by-collect")
    @Operation(summary = "获得自己某个收藏夹下的菜谱分页")
    public CommonResult<PageResult<AppRecipeRespVO>> getSelfRecipePageByCollect(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getSelfRecipeDetailPageByCollect(getLoginUserId(),pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/get")
    @Operation(summary = "获取菜谱")
    public CommonResult<AppRecipeRespVO> createRecipe(@RequestParam("id") Long id) {
        AppRecipeRespVO recipe = appRecipeService.getRecipeDetail(getLoginUserId(), id);
        return success(recipe);
    }

    @GetMapping("/page")
    @Operation(summary = "获得所有可见的菜谱分页")
    @PermitAll
    public CommonResult<PageResult<AppRecipeRespVO>> getRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getRecipeDetailPage(getLoginUserId(),pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/page/self")
    @Operation(summary = "获得自己的菜谱分页")
    public CommonResult<PageResult<AppRecipeRespVO>> getSelfRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getSelfRecipeDetailPage(getLoginUserId(),pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/page/public")
    @Operation(summary = "获得公共菜谱分页")
    @PermitAll
    public CommonResult<PageResult<AppRecipeRespVO>> getPublicRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getPublicRecipeDetailPage(getLoginUserId(),pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/page/system")
    @Operation(summary = "获得系统菜谱分页")
    @PermitAll
    public CommonResult<PageResult<AppRecipeRespVO>> getSystemRecipePage(@Valid AppRecipePageReqVO pageReqVO) {
        PageResult<AppRecipeRespVO> pageResult = appRecipeService.getSystemRecipeDetailPage(pageReqVO);
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
        createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 设置状态
        return success(appRecipeService.createRecipe(createReqVO));
    }

    @PostMapping("/create-or-update")
    @Operation(summary = "创建或更新菜谱")
    public CommonResult<Long> createOrUpdateRecipe(@Valid @RequestBody AppRecipeSaveReqVO createReqVO) {
        return success(appRecipeService.createOrUpdateRecipe(getLoginUserId(), createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜谱")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteRecipe(@RequestParam("id") Long id) {
        appRecipeService.deleteRecipe(getLoginUserId(), id);
        return success(true);
    }
}
