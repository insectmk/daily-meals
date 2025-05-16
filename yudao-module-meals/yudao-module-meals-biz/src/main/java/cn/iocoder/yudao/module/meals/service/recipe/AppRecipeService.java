package cn.iocoder.yudao.module.meals.service.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePopularPublicReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeSaveReqVO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * APP 菜谱 Service 接口
 *
 * @author InsectMk
 */
public interface AppRecipeService {
    /**
     * 获取菜谱的详细信息
     * @param userId 用户
     * @param id 菜谱ID
     * @return 详细信息
     */
    AppRecipeRespVO getRecipeDetail(Long userId, Long id);

    /**
     * 获取菜谱的详细信息分页
     * @param userId 用户ID
     * @param pageReqVO 分页信息
     * @return 详细信息分页
     */
    PageResult<AppRecipeRespVO> getSelfRecipeDetailPage(Long userId, @Valid AppRecipePageReqVO pageReqVO);

    /**
     * 获取系统菜谱分页：菜谱类型为系统的
     * @param pageReqVO 分页信息
     * @return 详细信息分页
     */
    PageResult<AppRecipeRespVO> getSystemRecipeDetailPage(@Valid AppRecipePageReqVO pageReqVO);

    /**
     * 获取公共菜谱分页：菜谱类型为用户的，并且非当前用户的
     * @param userId 用户ID
     * @param pageReqVO 请求参数
     * @return
     */
    PageResult<AppRecipeRespVO> getPublicRecipeDetailPage(Long userId, @Valid AppRecipePageReqVO pageReqVO);

    /**
     * 获得所有可见的菜谱分页
     * @param userId
     * @param pageReqVO
     * @return
     */
    PageResult<AppRecipeRespVO> getRecipeDetailPage(Long userId, @Valid AppRecipePageReqVO pageReqVO);

    /**
     * 获得最热门的菜谱
     * @param reqVO 参数
     * @return
     */
    List<AppRecipeRespVO> getPopularPublicRecipesDetail(AppRecipePopularPublicReqVO reqVO);

    /**
     * 创建菜谱
     * @param createReqVO 菜谱信息
     * @return
     */
    Long createRecipe(@Valid AppRecipeSaveReqVO createReqVO);

    /**
     * 创建或者更新菜谱
     * @param userId 登录用户
     * @param createReqVO 菜谱数据
     * @return
     */
    Long createOrUpdateRecipe(Long userId, @Valid AppRecipeSaveReqVO createReqVO);
}
