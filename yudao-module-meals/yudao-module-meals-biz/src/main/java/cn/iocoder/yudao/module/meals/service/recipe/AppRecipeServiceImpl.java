package cn.iocoder.yudao.module.meals.service.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeFoodDetailRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDetailDO;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeFoodMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * APP菜谱 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppRecipeServiceImpl implements AppRecipeService {

    @Resource
    private RecipeMapper recipeMapper;
    @Resource
    private RecipeFoodMapper recipeFoodMapper;

    @Override
    public AppRecipeRespVO getRecipeDetail(Long id) {
        // 查询基础信息
        RecipeDO recipeDO = recipeMapper.selectById(id);
        AppRecipeRespVO appRecipeRespVO = BeanUtils.toBean(recipeDO, AppRecipeRespVO.class);
        // 查询食材信息
        List<RecipeFoodDetailDO> recipeFoods = recipeFoodMapper.selectJoinList(
                RecipeFoodDetailDO.class,
                new MPJLambdaWrapper<RecipeFoodDO>()
                        .selectAll(RecipeFoodDO.class) // 查询所有基础字段
                        .selectAs(FoodDO::getName, RecipeFoodDetailDO::getFoodName) // 食物的名称作为详细信息名称
                        .selectAs(FoodDO::getFoodUnit, RecipeFoodDetailDO::getFoodUnit) // 食物的单位作为详细信息单位
                        .leftJoin(FoodDO.class, FoodDO::getId, RecipeFoodDO::getFoodId) // 联表 WHERE meals_recipe_food.food_id = meals_food.id
                        .eq(RecipeFoodDO::getRecipeId, id));
        // 拼装信息
        appRecipeRespVO.setFoods(BeanUtils.toBean(recipeFoods, AppRecipeFoodDetailRespVO.class));
        return appRecipeRespVO;
    }

    @Override
    public PageResult<RecipeDO> getRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        return recipeMapper.selectPage(userId, pageReqVO);
    }
}
