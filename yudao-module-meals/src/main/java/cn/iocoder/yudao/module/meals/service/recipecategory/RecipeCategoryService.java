package cn.iocoder.yudao.module.meals.service.recipecategory;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.recipecategory.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipecategory.RecipeCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 菜谱分类 Service 接口
 *
 * @author 珍珍
 */
public interface RecipeCategoryService {

    /**
     * 创建菜谱分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRecipeCategory(@Valid RecipeCategorySaveReqVO createReqVO);

    /**
     * 更新菜谱分类
     *
     * @param updateReqVO 更新信息
     */
    void updateRecipeCategory(@Valid RecipeCategorySaveReqVO updateReqVO);

    /**
     * 删除菜谱分类
     *
     * @param id 编号
     */
    void deleteRecipeCategory(Long id);

    /**
     * 获得菜谱分类
     *
     * @param id 编号
     * @return 菜谱分类
     */
    RecipeCategoryDO getRecipeCategory(Long id);

    /**
     * 获得菜谱分类列表
     *
     * @param listReqVO 查询条件
     * @return 菜谱分类列表
     */
    List<RecipeCategoryDO> getRecipeCategoryList(RecipeCategoryListReqVO listReqVO);

}