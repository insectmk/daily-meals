package cn.iocoder.yudao.module.meals.service.menurecipe;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 菜单菜谱 Service 接口
 *
 * @author InsectMk
 */
public interface MenuRecipeService {

    /**
     * 创建菜单菜谱
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMenuRecipe(@Valid MenuRecipeSaveReqVO createReqVO);

    /**
     * 更新菜单菜谱
     *
     * @param updateReqVO 更新信息
     */
    void updateMenuRecipe(@Valid MenuRecipeSaveReqVO updateReqVO);

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
    PageResult<MenuRecipeDO> getMenuRecipePage(MenuRecipePageReqVO pageReqVO);

}