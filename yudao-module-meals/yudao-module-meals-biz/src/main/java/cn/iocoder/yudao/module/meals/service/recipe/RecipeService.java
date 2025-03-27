package cn.iocoder.yudao.module.meals.service.recipe;

import java.util.*;

import cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo.AppDailyPlanPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplan.DailyPlanDO;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 菜谱 Service 接口
 *
 * @author InsectMk
 */
public interface RecipeService {

    /**
     * 创建菜谱
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRecipe(@Valid RecipeSaveReqVO createReqVO);

    /**
     * 更新菜谱
     *
     * @param updateReqVO 更新信息
     */
    void updateRecipe(@Valid RecipeSaveReqVO updateReqVO);

    /**
     * 删除菜谱
     *
     * @param id 编号
     */
    void deleteRecipe(Long id);

    /**
     * 获得菜谱
     *
     * @param id 编号
     * @return 菜谱
     */
    RecipeDO getRecipe(Long id);

    /**
     * 获得菜谱分页
     *
     * @param pageReqVO 分页查询
     * @return 菜谱分页
     */
    PageResult<RecipeDO> getRecipePage(RecipePageReqVO pageReqVO);

    // ==================== 子表（菜谱食材） ====================

    /**
     * 获得菜谱食材分页
     *
     * @param pageReqVO 分页查询
     * @param recipeId 菜谱ID
     * @return 菜谱食材分页
     */
    PageResult<RecipeFoodDO> getRecipeFoodPage(PageParam pageReqVO, Long recipeId);

    /**
     * 创建菜谱食材
     *
     * @param recipeFood 创建信息
     * @return 编号
     */
    Long createRecipeFood(@Valid RecipeFoodDO recipeFood);

    /**
     * 更新菜谱食材
     *
     * @param recipeFood 更新信息
     */
    void updateRecipeFood(@Valid RecipeFoodDO recipeFood);

    /**
     * 删除菜谱食材
     *
     * @param id 编号
     */
    void deleteRecipeFood(Long id);

	/**
	 * 获得菜谱食材
	 *
	 * @param id 编号
     * @return 菜谱食材
	 */
    RecipeFoodDO getRecipeFood(Long id);
}
