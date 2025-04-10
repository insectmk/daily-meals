package cn.iocoder.yudao.module.meals.convert.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipeFoodRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeFoodDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDetailDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.*;

/**
 * @Title: ReceipeConvert
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.convert.recipe
 * @Date 2025/3/21 10:05
 * @description: 食谱对象转换
 */
@Mapper
public interface RecipeConvert {
    RecipeConvert INSTANCE = Mappers.getMapper(RecipeConvert.class);

    /**
     * 将普通的菜谱分页，转换为带详细信息的菜谱分页
     * @param pageResult 原始分页对象
     * @param recipeFoods 食谱食材信息
     * @return 带详细信息的食谱分页对象
     */
    default PageResult<AppRecipeRespVO> convertPage(PageResult<RecipeDO> pageResult, List<RecipeFoodDetailDO> recipeFoods) {
        // 转化为目标类型的Page分页对象
        PageResult<AppRecipeRespVO> result = BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        // 获取所有的菜谱
        List<AppRecipeRespVO> recipes = result.getList();
        // 将对应的食材信息装载到对应的食谱上
        Map<Long, List<RecipeFoodDetailDO>> recipeFoodDetailDOMap = convertMultiMap(recipeFoods, RecipeFoodDetailDO::getRecipeId); // key：食谱ID，value：食材信息集合
        recipes.forEach(recipe -> {
            // 将食材信息装载到菜谱上
            recipe.setFoods(BeanUtils.toBean(recipeFoodDetailDOMap.get(recipe.getId()), AppRecipeFoodDetailRespVO.class));
        });
        return result;
    }

    /**
     * 将普通的菜谱集合，转换为带详细信息的菜谱集合
     * @param recipeList 菜谱集合
     * @param recipeFoods 菜谱食材集合
     * @return
     */
    default List<AppRecipeRespVO> convertList(List<RecipeDO> recipeList, List<RecipeFoodDetailDO> recipeFoods) {
        // 获取所有的菜谱
        List<AppRecipeRespVO> resultList = BeanUtils.toBean(recipeList, AppRecipeRespVO.class);
        // 将对应的食材信息装载到对应的食谱上
        Map<Long, List<RecipeFoodDetailDO>> recipeFoodDetailDOMap = convertMultiMap(recipeFoods, RecipeFoodDetailDO::getRecipeId); // key：食谱ID，value：食材信息集合
        resultList.forEach(recipe -> {
            // 将食材信息装载到菜谱上
            recipe.setFoods(BeanUtils.toBean(recipeFoodDetailDOMap.get(recipe.getId()), AppRecipeFoodDetailRespVO.class));
        });
        return resultList;
    }

    /**
     * 将普通菜谱食材分页信息转为详细信息
     * @param pageResult 菜谱食材分页
     * @param foods 食材信息
     * @return
     */
    default PageResult<RecipeFoodRespVO> convertRecipeFoodPage(PageResult<RecipeFoodDO> pageResult,
                                                               List<FoodDO> foods) {
        // 转换为VO分页
        PageResult<RecipeFoodRespVO> result = BeanUtils.toBean(pageResult, RecipeFoodRespVO.class);
        // 处理关联数据
        Map<Long, FoodDO> foodMap = convertMap(foods, FoodDO::getId); // 食材
        // 填充关联数据
        result.getList().forEach(recipeFood -> {
            FoodDO foodDO = foodMap.get(recipeFood.getFoodId()); // 获取食材
            recipeFood.setFoodName(foodDO.getName());// 食材名称
            recipeFood.setFoodUnit(foodDO.getFoodUnit()); // 食材单位
        });
        return result;
    }
}
