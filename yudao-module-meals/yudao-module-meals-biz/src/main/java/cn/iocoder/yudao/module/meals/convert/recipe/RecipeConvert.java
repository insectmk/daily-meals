package cn.iocoder.yudao.module.meals.convert.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipeSimpleRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeFoodDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMultiMap;

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
    default PageResult<AppRecipeRespVO> convertPage(PageResult<RecipeDO> pageResult, List<RecipeFoodDO> recipeFoods) {
        // 转化为目标类型的Page分页对象
        PageResult<AppRecipeRespVO> result = BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        // 获取所有的菜谱
        List<AppRecipeRespVO> recipes = result.getList();
        // 将对应的食材信息装载到对应的食谱上
        Map<Long, List<RecipeFoodDO>> recipeFoodDetailDOMap = convertMultiMap(recipeFoods, RecipeFoodDO::getRecipeId); // key：食谱ID，value：食材信息集合
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
    default List<AppRecipeRespVO> convertList(List<RecipeDO> recipeList, List<RecipeFoodDO> recipeFoods) {
        // 获取所有的菜谱
        List<AppRecipeRespVO> resultList = BeanUtils.toBean(recipeList, AppRecipeRespVO.class);
        // 将对应的食材信息装载到对应的食谱上
        Map<Long, List<RecipeFoodDO>> recipeFoodDetailDOMap = convertMultiMap(recipeFoods, RecipeFoodDO::getRecipeId); // key：食谱ID，value：食材信息集合
        resultList.forEach(recipe -> {
            // 将食材信息装载到菜谱上
            recipe.setFoods(BeanUtils.toBean(recipeFoodDetailDOMap.get(recipe.getId()), AppRecipeFoodDetailRespVO.class));
        });
        return resultList;
    }

    /**
     * 将菜谱信息转为菜谱精简信息列表
     * @param list
     * @return
     */
    List<RecipeSimpleRespVO> convertSimpleList(List<RecipeDO> list);
}
