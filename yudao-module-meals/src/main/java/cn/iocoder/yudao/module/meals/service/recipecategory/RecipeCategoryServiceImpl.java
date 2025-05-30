package cn.iocoder.yudao.module.meals.service.recipecategory;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.admin.recipecategory.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipecategory.RecipeCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.recipecategory.RecipeCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

/**
 * 菜谱分类 Service 实现类
 *
 * @author 珍珍
 */
@Service
@Validated
public class RecipeCategoryServiceImpl implements RecipeCategoryService {

    @Resource
    private RecipeCategoryMapper recipeCategoryMapper;

    @Override
    public Long createRecipeCategory(RecipeCategorySaveReqVO createReqVO) {
        // 校验父分类编号的有效性
        validateParentRecipeCategory(null, createReqVO.getParentId());
        // 校验分类名称的唯一性
        validateRecipeCategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        RecipeCategoryDO recipeCategory = BeanUtils.toBean(createReqVO, RecipeCategoryDO.class);
        recipeCategoryMapper.insert(recipeCategory);
        // 返回
        return recipeCategory.getId();
    }

    @Override
    public void updateRecipeCategory(RecipeCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateRecipeCategoryExists(updateReqVO.getId());
        // 校验父分类编号的有效性
        validateParentRecipeCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验分类名称的唯一性
        validateRecipeCategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        RecipeCategoryDO updateObj = BeanUtils.toBean(updateReqVO, RecipeCategoryDO.class);
        recipeCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteRecipeCategory(Long id) {
        // 校验存在
        validateRecipeCategoryExists(id);
        // 校验是否有子菜谱分类
        if (recipeCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(RECIPE_CATEGORY_EXITS_CHILDREN);
        }
        // 删除
        recipeCategoryMapper.deleteById(id);
    }

    private void validateRecipeCategoryExists(Long id) {
        if (recipeCategoryMapper.selectById(id) == null) {
            throw exception(RECIPE_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentRecipeCategory(Long id, Long parentId) {
        if (parentId == null || RecipeCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父菜谱分类
        if (Objects.equals(id, parentId)) {
            throw exception(RECIPE_CATEGORY_PARENT_ERROR);
        }
        // 2. 父菜谱分类不存在
        RecipeCategoryDO parentRecipeCategory = recipeCategoryMapper.selectById(parentId);
        if (parentRecipeCategory == null) {
            throw exception(RECIPE_CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父菜谱分类，如果父菜谱分类是自己的子菜谱分类，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentRecipeCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(RECIPE_CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父菜谱分类
            if (parentId == null || RecipeCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentRecipeCategory = recipeCategoryMapper.selectById(parentId);
            if (parentRecipeCategory == null) {
                break;
            }
        }
    }

    private void validateRecipeCategoryNameUnique(Long id, Long parentId, String name) {
        RecipeCategoryDO recipeCategory = recipeCategoryMapper.selectByParentIdAndName(parentId, name);
        if (recipeCategory == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜谱分类
        if (id == null) {
            throw exception(RECIPE_CATEGORY_NAME_DUPLICATE);
        }
        if (!Objects.equals(recipeCategory.getId(), id)) {
            throw exception(RECIPE_CATEGORY_NAME_DUPLICATE);
        }
    }

    @Override
    public RecipeCategoryDO getRecipeCategory(Long id) {
        return recipeCategoryMapper.selectById(id);
    }

    @Override
    public List<RecipeCategoryDO> getRecipeCategoryList(RecipeCategoryListReqVO listReqVO) {
        return recipeCategoryMapper.selectList(listReqVO);
    }

}