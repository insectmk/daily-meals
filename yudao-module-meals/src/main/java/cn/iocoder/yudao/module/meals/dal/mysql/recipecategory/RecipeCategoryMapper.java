package cn.iocoder.yudao.module.meals.dal.mysql.recipecategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipecategory.RecipeCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.admin.recipecategory.vo.*;

/**
 * 菜谱分类 Mapper
 *
 * @author 珍珍
 */
@Mapper
public interface RecipeCategoryMapper extends BaseMapperX<RecipeCategoryDO> {

    default List<RecipeCategoryDO> selectList(RecipeCategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RecipeCategoryDO>()
                .eqIfPresent(RecipeCategoryDO::getParentId, reqVO.getParentId())
                .likeIfPresent(RecipeCategoryDO::getName, reqVO.getName())
                .eqIfPresent(RecipeCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecipeCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeCategoryDO::getId));
    }

	default RecipeCategoryDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(RecipeCategoryDO::getParentId, parentId, RecipeCategoryDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(RecipeCategoryDO::getParentId, parentId);
    }

}