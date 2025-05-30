package cn.iocoder.yudao.module.meals.service.food;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.meals.controller.admin.food.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.meals.dal.mysql.food.FoodMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.*;

/**
 * 食材 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class FoodServiceImpl implements FoodService {

    @Resource
    private FoodMapper foodMapper;

    @Override
    public Long createFood(FoodSaveReqVO createReqVO) {
        // 插入
        FoodDO food = BeanUtils.toBean(createReqVO, FoodDO.class);
        foodMapper.insert(food);
        // 返回
        return food.getId();
    }

    @Override
    public void updateFood(FoodSaveReqVO updateReqVO) {
        // 校验存在
        validateFoodExists(updateReqVO.getId());
        // 更新
        FoodDO updateObj = BeanUtils.toBean(updateReqVO, FoodDO.class);
        foodMapper.updateById(updateObj);
    }

    @Override
    public void deleteFood(Long id) {
        // 校验存在
        validateFoodExists(id);
        // 删除
        foodMapper.deleteById(id);
    }

    private void validateFoodExists(Long id) {
        if (foodMapper.selectById(id) == null) {
            throw exception(FOOD_NOT_EXISTS);
        }
    }

    @Override
    public FoodDO getFood(Long id) {
        return foodMapper.selectById(id);
    }

    @Override
    public PageResult<FoodDO> getFoodPage(FoodPageReqVO pageReqVO) {
        return foodMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FoodDO> getFoodList() {
        return foodMapper.selectList();
    }

    @Override
    public List<FoodDO> getFoodList(Collection<Long> ids) {
        return foodMapper.selectList(new LambdaQueryWrapperX<FoodDO>()
                .in(FoodDO::getId, ids));
    }

}
