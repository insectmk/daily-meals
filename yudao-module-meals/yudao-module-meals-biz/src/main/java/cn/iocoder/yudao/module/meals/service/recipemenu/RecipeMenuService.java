package cn.iocoder.yudao.module.meals.service.recipemenu;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

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

}