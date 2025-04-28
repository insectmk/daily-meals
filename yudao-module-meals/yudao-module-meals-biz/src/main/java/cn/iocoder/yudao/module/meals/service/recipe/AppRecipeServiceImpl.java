package cn.iocoder.yudao.module.meals.service.recipe;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.*;
import cn.iocoder.yudao.module.meals.convert.recipe.RecipeConvert;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.PopularPublicRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDetailDO;
import cn.iocoder.yudao.module.meals.dal.mysql.dailyplanitem.DailyPlanItemMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeFoodMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeMapper;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

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
    @Resource
    private DailyPlanItemMapper dailyPlanItemMapper;

    @Override
    public AppRecipeRespVO getRecipeDetail(Long id) {
        // 查询基础信息并判断是否在今日计划中
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
    public PageResult<AppRecipeRespVO> getRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        pageReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 获取用户菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDetailDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getPublicRecipeDetailPage(AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        RecipePageReqVO pageReqVo1 = BeanUtils.toBean(pageReqVO, RecipePageReqVO.class);
        pageReqVo1.setRecipeType(RecipeTypesEnum.SYSTEM.getType()); // 获取系统菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(pageReqVo1);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDetailDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public List<AppRecipeRespVO> getPopularPublicRecipesDetail(AppRecipePopularPublicReqVO reqVO) {
        // 查询被加入计划最多的前len位菜谱
        List<PopularPublicRecipeDO> popularPublicRecipeDOS = dailyPlanItemMapper.selectPopularPublicRecipeList(reqVO);
        // 菜谱ID集合
        Set<Long> recipeIds = convertSet(popularPublicRecipeDOS, PopularPublicRecipeDO::getRecipeId);
        // 如果没有菜谱就返回空集合
        if (recipeIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<RecipeDO> recipeList = recipeMapper.selectList(new LambdaQueryWrapperX<RecipeDO>()
                .in(RecipeDO::getId, recipeIds));
        // 查询菜谱食材信息
        List<RecipeFoodDetailDO> recipeFoods = getRecipeFoodsByRecipeIds(recipeIds);
        // 装载信息
        return RecipeConvert.INSTANCE.convertList(recipeList,recipeFoods);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRecipe(AppRecipeSaveReqVO createReqVO) {
        // 插入基础数据
        RecipeDO recipe = BeanUtils.toBean(createReqVO, RecipeDO.class);
        recipeMapper.insert(recipe);
        Long recipeId = recipe.getId();
        // 插入菜谱食材数据
        for (AppRecipeFoodSaveReqVO recipeFood : createReqVO.getRecipeFoods()) {
            RecipeFoodDO recipeFoodDO = BeanUtils.toBean(recipeFood, RecipeFoodDO.class);
            recipeFoodDO.setRecipeId(recipeId); // 设置菜谱ID
            recipeFoodMapper.insert(recipeFoodDO);
        }
        // 返回
        return recipeId;
    }

    /**
     * 通过菜谱ID集合获取所有菜谱的食材信息
     * @param recipeIds 菜谱ID
     * @return 菜谱食材集合
     */
    private List<RecipeFoodDetailDO> getRecipeFoodsByRecipeIds(Set<Long> recipeIds) {
        return recipeFoodMapper.selectJoinList(
                RecipeFoodDetailDO.class,
                new MPJLambdaWrapper<RecipeFoodDO>()
                        .selectAll(RecipeFoodDO.class) // 查询所有基础字段
                        .selectAs(FoodDO::getName, RecipeFoodDetailDO::getFoodName) // 食物的名称作为详细信息名称
                        .selectAs(FoodDO::getFoodUnit, RecipeFoodDetailDO::getFoodUnit) // 食物的单位作为详细信息单位
                        .leftJoin(FoodDO.class, FoodDO::getId, RecipeFoodDO::getFoodId) // 联表 WHERE meals_recipe_food.food_id = meals_food.id
                        // 查询所有分页菜谱的食材信息
                        .in(RecipeFoodDO::getRecipeId, recipeIds));
    }
}
