package cn.iocoder.yudao.module.meals.dal.mysql.foodcategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.foodcategory.FoodCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo.*;

/**
 * 食材分类 Mapper
 *
 * @author 珍珍
 */
@Mapper
public interface FoodCategoryMapper extends BaseMapperX<FoodCategoryDO> {

    default List<FoodCategoryDO> selectList(FoodCategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FoodCategoryDO>()
                .eqIfPresent(FoodCategoryDO::getParentId, reqVO.getParentId())
                .likeIfPresent(FoodCategoryDO::getName, reqVO.getName())
                .eqIfPresent(FoodCategoryDO::getPicUrl, reqVO.getPicUrl())
                .eqIfPresent(FoodCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(FoodCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(FoodCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FoodCategoryDO::getId));
    }

	default FoodCategoryDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(FoodCategoryDO::getParentId, parentId, FoodCategoryDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(FoodCategoryDO::getParentId, parentId);
    }

}