package cn.iocoder.yudao.module.meals.service.menurecipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.menurecipe.vo.AppMenuRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.menurecipe.vo.AppMenuRecipeSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import jakarta.validation.Valid;

/**
 * 菜单菜谱 Service 接口
 *
 * @author InsectMk
 */
public interface AppMenuRecipeService {

    /**
     * 创建菜单菜谱
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMenuRecipe(@Valid AppMenuRecipeSaveReqVO createReqVO);

    /**
     * 更新菜单菜谱
     *
     * @param updateReqVO 更新信息
     */
    void updateMenuRecipe(@Valid AppMenuRecipeSaveReqVO updateReqVO);

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

    /**
     * 获得菜单菜谱分页
     *
     * @param pageReqVO 分页查询
     * @return 菜单菜谱分页
     */
    PageResult<MenuRecipeDO> getMenuRecipePage(AppMenuRecipePageReqVO pageReqVO);

}
