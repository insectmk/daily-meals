package cn.iocoder.yudao.module.meals.service.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo.MenuRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.RecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.RecipeMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import jakarta.validation.Valid;

/**
 * 菜谱菜单 Service 接口
 *
 * @author InsectMk
 */
public interface RecipeMenuService {

    /**
     * 创建菜谱菜单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRecipeMenu(@Valid RecipeMenuSaveReqVO createReqVO);

    /**
     * 更新菜谱菜单
     *
     * @param updateReqVO 更新信息
     */
    void updateRecipeMenu(@Valid RecipeMenuSaveReqVO updateReqVO);

    /**
     * 删除菜谱菜单
     *
     * @param id 编号
     */
    void deleteRecipeMenu(Long id);

    /**
     * 获得菜谱菜单
     *
     * @param id 编号
     * @return 菜谱菜单
     */
    RecipeMenuDO getRecipeMenu(Long id);

    /**
     * 获得菜谱菜单分页
     *
     * @param pageReqVO 分页查询
     * @return 菜谱菜单分页
     */
    PageResult<RecipeMenuDO> getRecipeMenuPage(RecipeMenuPageReqVO pageReqVO);

    // ==================== 子表（菜单菜谱） ====================

    /**
     * 获得菜单菜谱分页
     *
     * @param pageReqVO 分页查询
     * @param recipeMenuId 菜谱菜单编号
     * @return 菜单菜谱分页
     */
    PageResult<MenuRecipeDO> getMenuRecipePage(MenuRecipePageReqVO pageReqVO, Long recipeMenuId);

    /**
     * 创建菜单菜谱
     *
     * @param menuRecipe 创建信息
     * @return 编号
     */
    Long createMenuRecipe(@Valid MenuRecipeDO menuRecipe);

    /**
     * 更新菜单菜谱
     *
     * @param menuRecipe 更新信息
     */
    void updateMenuRecipe(@Valid MenuRecipeDO menuRecipe);

    /**
     * 删除菜单菜谱
     *
     * @param id 编号
     */
    void deleteMenuRecipe(Long id);

	/**
	 * 获得菜单菜谱
	 *
	 * @param id 编号
     * @return 菜单菜谱
	 */
    MenuRecipeDO getMenuRecipe(Long id);

}
