package cn.iocoder.yudao.module.meals.service.foodcategory;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.foodcategory.FoodCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 食材分类 Service 接口
 *
 * @author 珍珍
 */
public interface FoodCategoryService {

    /**
     * 创建食材分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFoodCategory(@Valid FoodCategorySaveReqVO createReqVO);

    /**
     * 更新食材分类
     *
     * @param updateReqVO 更新信息
     */
    void updateFoodCategory(@Valid FoodCategorySaveReqVO updateReqVO);

    /**
     * 删除食材分类
     *
     * @param id 编号
     */
    void deleteFoodCategory(Long id);

    /**
     * 获得食材分类
     *
     * @param id 编号
     * @return 食材分类
     */
    FoodCategoryDO getFoodCategory(Long id);

    /**
     * 获得食材分类列表
     *
     * @param listReqVO 查询条件
     * @return 食材分类列表
     */
    List<FoodCategoryDO> getFoodCategoryList(FoodCategoryListReqVO listReqVO);

}