package cn.iocoder.yudao.module.meals.dal.mysql.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 查询菜谱食材详细信息
     * @param recipeId 菜谱ID
     * @return 菜谱食材详细信息
     */
    default List<RecipeFoodDO> selectDetailByRecipeId(Long recipeId) {
        return this.selectJoinList(RecipeFoodDO.class, new MPJLambdaWrapper<RecipeFoodDO>()
                .selectAll(RecipeFoodDO.class) // 查询所有基础字段
                .selectAs(FoodDO::getName, RecipeFoodDO::getFoodName) // 食材名称
        );
    }
}
