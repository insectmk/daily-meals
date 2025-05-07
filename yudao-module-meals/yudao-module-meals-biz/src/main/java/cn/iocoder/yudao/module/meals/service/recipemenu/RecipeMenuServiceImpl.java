package cn.iocoder.yudao.module.meals.service.recipemenu;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.recipemenu.RecipeMenuMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

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
    public void deleteRecipeMenu(Long id) {
        // 校验存在
        validateRecipeMenuExists(id);
        // 删除
        recipeMenuMapper.deleteById(id);
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

}