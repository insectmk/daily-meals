package cn.iocoder.yudao.module.meals.service.recipe;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.*;
import cn.iocoder.yudao.module.meals.convert.recipe.RecipeConvert;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.PopularPublicRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.mysql.dailyplanitem.DailyPlanItemMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeFoodMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeMapper;
import cn.iocoder.yudao.module.meals.enums.RecipeStatusEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
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
        List<RecipeFoodDO> recipeFoods = recipeFoodMapper.selectList(new LambdaQueryWrapperX<RecipeFoodDO>()
                .eqIfPresent(RecipeFoodDO::getRecipeId, id));
        // 拼装信息
        appRecipeRespVO.setFoods(BeanUtils.toBean(recipeFoods, AppRecipeFoodDetailRespVO.class));
        return appRecipeRespVO;
    }

    @Override
    public PageResult<AppRecipeRespVO> getSelfRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        pageReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 获取用户菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getSystemRecipeDetailPage(AppRecipePageReqVO pageReqVO) {
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
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getPublicRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        pageReqVO.setStatus(RecipeStatusEnum.PUBLIC.getType()); // 公开的菜谱
        pageReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 用户菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        PageResult<RecipeDO> pageResult = recipeMapper.getUserViewableRecipePage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
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
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(recipeIds);
        // 装载信息
        return RecipeConvert.INSTANCE.convertList(recipeList,recipeFoods);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRecipe(AppRecipeSaveReqVO createReqVO) {
        createReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 创建为用户类型菜谱
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
    private List<RecipeFoodDO> getRecipeFoodsByRecipeIds(Set<Long> recipeIds) {
        return recipeFoodMapper.selectList(new LambdaQueryWrapperX<RecipeFoodDO>()
                // 查询所有分页菜谱的食材信息
                .in(RecipeFoodDO::getRecipeId, recipeIds));
    }
}
