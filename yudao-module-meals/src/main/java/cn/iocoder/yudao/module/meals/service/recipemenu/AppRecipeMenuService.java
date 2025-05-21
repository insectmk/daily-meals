package cn.iocoder.yudao.module.meals.service.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeSaveReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSimpleRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 菜谱菜单 Service 接口
 *
 * @author InsectMk
 */
public interface AppRecipeMenuService {

    /**
     * 创建菜谱菜单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRecipeMenu(@Valid AppRecipeMenuSaveReqVO createReqVO);

    /**
     * 更新菜谱菜单
     *
     * @param updateReqVO 更新信息
     */
    void updateRecipeMenu(@Valid AppRecipeMenuSaveReqVO updateReqVO);

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
     * 获得用户可见菜谱菜单分页
     *
     * @param userId 用户ID
     * @param pageReqVO 分页查询
     * @return 菜谱菜单分页
     */
    PageResult<RecipeMenuDO> getUserViewableRecipeMenuPage(Long userId, AppRecipeMenuPageReqVO pageReqVO);

    /**
     * 获得用户自己的菜谱菜单分页
     * @param userId 用户ID
     * @param pageReqVO 分页对象
     * @return 菜谱菜单分页
     */
    PageResult<RecipeMenuDO> getUserSelfRecipeMenuPage(Long userId, @Valid AppRecipeMenuPageReqVO pageReqVO);

    /**
     * 获取自己菜谱菜单精简信息列表
     *
     * @param userId 用户ID
     * @return 自己菜谱菜单精简信息列表
     */
    List<RecipeMenuDO> getSelfRecipeMenuList(Long userId);

    /**
     * 获取菜谱菜单详细信息
     * @param userId 用户ID
     * @param id 菜谱菜单ID
     * @return 详细信息
     */
    AppRecipeMenuRespVO getRecipeMenuDetail(Long userId, Long id);

    /**
     * 创建或者更新菜谱菜单
     * @param userId 用户ID
     * @param createReqVO 参数
     * @return
     */
    Long createOrUpdateRecipe(Long userId, @Valid AppRecipeMenuSaveReqVO createReqVO);

    /**
     * 删除菜谱菜单
     * @param userId 用户ID
     * @param id 菜谱菜单ID
     */
    void deleteRecipeMenu(Long userId, Long id);
}
