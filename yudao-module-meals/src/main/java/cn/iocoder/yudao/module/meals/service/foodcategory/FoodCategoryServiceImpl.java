package cn.iocoder.yudao.module.meals.service.foodcategory;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.foodcategory.FoodCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.foodcategory.FoodCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

/**
 * 食材分类 Service 实现类
 *
 * @author 珍珍
 */
@Service
@Validated
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Resource
    private FoodCategoryMapper foodCategoryMapper;

    @Override
    public Long createFoodCategory(FoodCategorySaveReqVO createReqVO) {
        // 校验父分类编号的有效性
        validateParentFoodCategory(null, createReqVO.getParentId());
        // 校验分类名称的唯一性
        validateFoodCategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        FoodCategoryDO foodCategory = BeanUtils.toBean(createReqVO, FoodCategoryDO.class);
        foodCategoryMapper.insert(foodCategory);
        // 返回
        return foodCategory.getId();
    }

    @Override
    public void updateFoodCategory(FoodCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateFoodCategoryExists(updateReqVO.getId());
        // 校验父分类编号的有效性
        validateParentFoodCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验分类名称的唯一性
        validateFoodCategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        FoodCategoryDO updateObj = BeanUtils.toBean(updateReqVO, FoodCategoryDO.class);
        foodCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteFoodCategory(Long id) {
        // 校验存在
        validateFoodCategoryExists(id);
        // 校验是否有子食材分类
        if (foodCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(FOOD_CATEGORY_EXITS_CHILDREN);
        }
        // 删除
        foodCategoryMapper.deleteById(id);
    }

    private void validateFoodCategoryExists(Long id) {
        if (foodCategoryMapper.selectById(id) == null) {
            throw exception(FOOD_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentFoodCategory(Long id, Long parentId) {
        if (parentId == null || FoodCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父食材分类
        if (Objects.equals(id, parentId)) {
            throw exception(FOOD_CATEGORY_PARENT_ERROR);
        }
        // 2. 父食材分类不存在
        FoodCategoryDO parentFoodCategory = foodCategoryMapper.selectById(parentId);
        if (parentFoodCategory == null) {
            throw exception(FOOD_CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父食材分类，如果父食材分类是自己的子食材分类，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentFoodCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(FOOD_CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父食材分类
            if (parentId == null || FoodCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentFoodCategory = foodCategoryMapper.selectById(parentId);
            if (parentFoodCategory == null) {
                break;
            }
        }
    }

    private void validateFoodCategoryNameUnique(Long id, Long parentId, String name) {
        FoodCategoryDO foodCategory = foodCategoryMapper.selectByParentIdAndName(parentId, name);
        if (foodCategory == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的食材分类
        if (id == null) {
            throw exception(FOOD_CATEGORY_NAME_DUPLICATE);
        }
        if (!Objects.equals(foodCategory.getId(), id)) {
            throw exception(FOOD_CATEGORY_NAME_DUPLICATE);
        }
    }

    @Override
    public FoodCategoryDO getFoodCategory(Long id) {
        return foodCategoryMapper.selectById(id);
    }

    @Override
    public List<FoodCategoryDO> getFoodCategoryList(FoodCategoryListReqVO listReqVO) {
        return foodCategoryMapper.selectList(listReqVO);
    }

}