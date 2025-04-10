package cn.iocoder.yudao.module.meals.service.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePopularPublicReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
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
     * @param id 菜谱ID
     * @return 详细信息
     */
    AppRecipeRespVO getRecipeDetail(Long id);

    /**
     * 获取菜谱的详细信息分页
     * @param userId 用户ID
     * @param pageReqVO 分页信息
     * @return 详细信息分页
     */
    PageResult<AppRecipeRespVO> getRecipeDetailPage(Long userId, @Valid AppRecipePageReqVO pageReqVO);

    /**
     * 获取公共菜谱分页（没有用户ID的）
     * @param pageReqVO 分页信息
     * @return 详细信息分页
     */
    PageResult<AppRecipeRespVO> getPublicRecipeDetailPage(@Valid AppRecipePageReqVO pageReqVO);

    /**
     * 获得最热门的菜谱
     * @param reqVO 参数
     * @return
     */
    List<AppRecipeRespVO> getPopularPublicRecipesDetail(AppRecipePopularPublicReqVO reqVO);
}
