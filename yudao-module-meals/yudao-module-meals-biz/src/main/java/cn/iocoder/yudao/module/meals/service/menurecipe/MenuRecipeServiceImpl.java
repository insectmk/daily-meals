package cn.iocoder.yudao.module.meals.service.menurecipe;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.menurecipe.MenuRecipeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

/**
 * 菜单菜谱 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class MenuRecipeServiceImpl implements MenuRecipeService {

    @Resource
    private MenuRecipeMapper menuRecipeMapper;

    @Override
    public Long createMenuRecipe(MenuRecipeSaveReqVO createReqVO) {
        // 插入
        MenuRecipeDO menuRecipe = BeanUtils.toBean(createReqVO, MenuRecipeDO.class);
        menuRecipeMapper.insert(menuRecipe);
        // 返回
        return menuRecipe.getId();
    }

    @Override
    public void updateMenuRecipe(MenuRecipeSaveReqVO updateReqVO) {
        // 校验存在
        validateMenuRecipeExists(updateReqVO.getId());
        // 更新
        MenuRecipeDO updateObj = BeanUtils.toBean(updateReqVO, MenuRecipeDO.class);
        menuRecipeMapper.updateById(updateObj);
    }

    @Override
    public void deleteMenuRecipe(Long id) {
        // 校验存在
        validateMenuRecipeExists(id);
        // 删除
        menuRecipeMapper.deleteById(id);
    }

    private void validateMenuRecipeExists(Long id) {
        if (menuRecipeMapper.selectById(id) == null) {
            throw exception(MENU_RECIPE_NOT_EXISTS);
        }
    }

    @Override
    public MenuRecipeDO getMenuRecipe(Long id) {
        return menuRecipeMapper.selectById(id);
    }

    @Override
    public PageResult<MenuRecipeDO> getMenuRecipePage(MenuRecipePageReqVO pageReqVO) {
        return menuRecipeMapper.selectPage(pageReqVO);
    }

}