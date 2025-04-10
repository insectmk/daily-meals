package cn.iocoder.yudao.module.meals.service.food;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.food.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 食材 Service 接口
 *
 * @author InsectMk
 */
public interface FoodService {

    /**
     * 创建食材
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFood(@Valid FoodSaveReqVO createReqVO);

    /**
     * 更新食材
     *
     * @param updateReqVO 更新信息
     */
    void updateFood(@Valid FoodSaveReqVO updateReqVO);

    /**
     * 删除食材
     *
     * @param id 编号
     */
    void deleteFood(Long id);

    /**
     * 获得食材
     *
     * @param id 编号
     * @return 食材
     */
    FoodDO getFood(Long id);

    /**
     * 获得食材分页
     *
     * @param pageReqVO 分页查询
     * @return 食材分页
     */
    PageResult<FoodDO> getFoodPage(FoodPageReqVO pageReqVO);

    /**
     * 获得所有食材
     * @return
     */
    List<FoodDO> getFoodList();
}
