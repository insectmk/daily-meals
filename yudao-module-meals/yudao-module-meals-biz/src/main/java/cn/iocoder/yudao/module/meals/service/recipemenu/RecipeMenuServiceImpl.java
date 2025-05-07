package cn.iocoder.yudao.module.meals.service.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo.MenuRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.RecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.RecipeMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.module.meals.dal.mysql.menurecipe.MenuRecipeMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipemenu.RecipeMenuMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.MENU_RECIPE_NOT_EXISTS;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.RECIPE_MENU_NOT_EXISTS;

/**
 * 菜谱菜单 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class RecipeMenuServiceImpl implements RecipeMenuService {

    @Resource
    private RecipeMenuMapper recipeMenuMapper;
    @Resource
    private MenuRecipeMapper menuRecipeMapper;

    @Override
    public Long createRecipeMenu(RecipeMenuSaveReqVO createReqVO) {
        // 插入
        RecipeMenuDO recipeMenu = BeanUtils.toBean(createReqVO, RecipeMenuDO.class);
        recipeMenuMapper.insert(recipeMenu);
        // 返回
        return recipeMenu.getId();
    }

    @Override
    public void updateRecipeMenu(RecipeMenuSaveReqVO updateReqVO) {
        // 校验存在
        validateRecipeMenuExists(updateReqVO.getId());
        // 更新
        RecipeMenuDO updateObj = BeanUtils.toBean(updateReqVO, RecipeMenuDO.class);
        recipeMenuMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecipeMenu(Long id) {
        // 校验存在
        validateRecipeMenuExists(id);
        // 删除
        recipeMenuMapper.deleteById(id);

        // 删除子表
        deleteMenuRecipeByRecipeMenuId(id);
    }

    private void validateRecipeMenuExists(Long id) {
        if (recipeMenuMapper.selectById(id) == null) {
            throw exception(RECIPE_MENU_NOT_EXISTS);
        }
    }

    @Override
    public RecipeMenuDO getRecipeMenu(Long id) {
        return recipeMenuMapper.selectById(id);
    }

    @Override
    public PageResult<RecipeMenuDO> getRecipeMenuPage(RecipeMenuPageReqVO pageReqVO) {
        return recipeMenuMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（菜单菜谱） ====================

    @Override
    public PageResult<MenuRecipeDO> getMenuRecipePage(MenuRecipePageReqVO pageReqVO, Long recipeMenuId) {
        return menuRecipeMapper.selectPage(pageReqVO, recipeMenuId);
    }

    @Override
    public Long createMenuRecipe(MenuRecipeDO menuRecipe) {
        menuRecipeMapper.insert(menuRecipe);
        return menuRecipe.getId();
    }

    @Override
    public void updateMenuRecipe(MenuRecipeDO menuRecipe) {
        // 校验存在
        validateMenuRecipeExists(menuRecipe.getId());
        // 更新
        menuRecipe.setUpdater(null).setUpdateTime(null); // 解决更新情况下：updateTime 不更新
        menuRecipeMapper.updateById(menuRecipe);
    }

    @Override
    public void deleteMenuRecipe(Long id) {
        // 校验存在
        validateMenuRecipeExists(id);
        // 删除
        menuRecipeMapper.deleteById(id);
    }

    @Override
    public MenuRecipeDO getMenuRecipe(Long id) {
        return menuRecipeMapper.selectById(id);
    }

    private void validateMenuRecipeExists(Long id) {
        if (menuRecipeMapper.selectById(id) == null) {
            throw exception(MENU_RECIPE_NOT_EXISTS);
        }
    }

    private void deleteMenuRecipeByRecipeMenuId(Long recipeMenuId) {
        menuRecipeMapper.deleteByRecipeMenuId(recipeMenuId);
    }

}
