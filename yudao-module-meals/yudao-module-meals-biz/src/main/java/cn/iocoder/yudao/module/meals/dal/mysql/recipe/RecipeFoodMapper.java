package cn.iocoder.yudao.module.meals.dal.mysql.recipe;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜谱食材 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface RecipeFoodMapper extends BaseMapperX<RecipeFoodDO> {

    default PageResult<RecipeFoodDO> selectPage(PageParam reqVO, Long recipeId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeFoodDO>()
            .eq(RecipeFoodDO::getRecipeId, recipeId)
            .orderByDesc(RecipeFoodDO::getId));
    }

    default int deleteByRecipeId(Long recipeId) {
        return delete(RecipeFoodDO::getRecipeId, recipeId);
    }

}