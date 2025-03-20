package cn.iocoder.yudao.module.meals.dal.mysql.recipe;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.*;

/**
 * 菜谱 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface RecipeMapper extends BaseMapperX<RecipeDO> {

    default PageResult<RecipeDO> selectPage(RecipePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeDO>()
                .likeIfPresent(RecipeDO::getName, reqVO.getName())
                .eqIfPresent(RecipeDO::getRecipeType, reqVO.getRecipeType())
                .eqIfPresent(RecipeDO::getRecipeLevel, reqVO.getRecipeLevel())
                .eqIfPresent(RecipeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecipeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeDO::getId));
    }

}
