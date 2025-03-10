package cn.iocoder.yudao.module.meals.dal.mysql.food;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.admin.food.vo.*;

/**
 * 食材 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface FoodMapper extends BaseMapperX<FoodDO> {

    default PageResult<FoodDO> selectPage(FoodPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FoodDO>()
                .likeIfPresent(FoodDO::getName, reqVO.getName())
                .eqIfPresent(FoodDO::getFoodType, reqVO.getFoodType())
                .eqIfPresent(FoodDO::getFoodUnit, reqVO.getFoodUnit())
                .betweenIfPresent(FoodDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FoodDO::getId));
    }

}