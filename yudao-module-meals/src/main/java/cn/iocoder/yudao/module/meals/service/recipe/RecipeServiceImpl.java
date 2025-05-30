package cn.iocoder.yudao.module.meals.service.recipe;

import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeFoodMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

/**
 * 菜谱 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class RecipeServiceImpl implements RecipeService {

    @Resource
    private RecipeMapper recipeMapper;
    @Resource
    private RecipeFoodMapper recipeFoodMapper;

    @Override
    public Long createRecipe(RecipeSaveReqVO createReqVO) {
        // 插入
        RecipeDO recipe = BeanUtils.toBean(createReqVO, RecipeDO.class);
        recipe.setRecipeType(RecipeTypesEnum.SYSTEM.getType()); // 设置为系统菜谱
        recipeMapper.insert(recipe);
        // 返回
        return recipe.getId();
    }

    @Override
    public void updateRecipe(RecipeSaveReqVO updateReqVO) {
        // 校验存在
        validateRecipeExists(updateReqVO.getId());
        // 更新
        RecipeDO updateObj = BeanUtils.toBean(updateReqVO, RecipeDO.class);
        recipeMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecipe(Long id) {
        // 校验存在
        validateRecipeExists(id);
        // 删除
        recipeMapper.deleteById(id);
        // 删除子表
        deleteRecipeFoodByRecipeId(id);
    }

    private void validateRecipeExists(Long id) {
        if (recipeMapper.selectById(id) == null) {
            throw exception(RECIPE_NOT_EXISTS);
        }
    }

    @Override
    public RecipeDO getRecipe(Long id) {
        return recipeMapper.selectById(id);
    }

    @Override
    public PageResult<RecipeDO> getRecipePage(RecipePageReqVO pageReqVO) {
        pageReqVO.setRecipeType(RecipeTypesEnum.SYSTEM.getType()); // 查询系统菜谱
        return recipeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RecipeDO> getRecipeList() {
        return recipeMapper.selectList();
    }

    // ==================== 子表（菜谱食材） ====================

    @Override
    public PageResult<RecipeFoodDO> getRecipeFoodPage(PageParam pageReqVO, Long recipeId) {
        return recipeFoodMapper.selectPage(pageReqVO, recipeId);
    }

    @Override
    public Long createRecipeFood(RecipeFoodDO recipeFood) {
        recipeFoodMapper.insert(recipeFood);
        return recipeFood.getId();
    }

    @Override
    public void updateRecipeFood(RecipeFoodDO recipeFood) {
        // 校验存在
        validateRecipeFoodExists(recipeFood.getId());
        // 更新
        recipeFood.setUpdater(null).setUpdateTime(null); // 解决更新情况下：updateTime 不更新
        recipeFoodMapper.updateById(recipeFood);
    }

    @Override
    public void deleteRecipeFood(Long id) {
        // 校验存在
        validateRecipeFoodExists(id);
        // 删除
        recipeFoodMapper.deleteById(id);
    }

    @Override
    public RecipeFoodDO getRecipeFood(Long id) {
        return recipeFoodMapper.selectById(id);
    }

    private void validateRecipeFoodExists(Long id) {
        if (recipeFoodMapper.selectById(id) == null) {
            throw exception(RECIPE_FOOD_NOT_EXISTS);
        }
    }

    private void deleteRecipeFoodByRecipeId(Long recipeId) {
        recipeFoodMapper.deleteByRecipeId(recipeId);
    }

}
