package cn.iocoder.yudao.module.meals.dal.mysql.recipe;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.enums.RecipeStatusEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * 分页查询用户可见的菜谱数据
     * @param userId 用户ID
     * @param pageReqVO 分页查询对象
     */
    default PageResult<RecipeDO> getUserViewableRecipePage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 处理 食材名称 foodNames 过滤条件
        String foodNameSql = "";
        if (CollUtil.isNotEmpty(pageReqVO.getFoodNames())) {
            // 关联查询食材
            foodNameSql = pageReqVO.getFoodNames().stream()
                    .map(foodName -> String.format("exists(select 1 from meals_recipe_food rf where rf.recipe_id = meals_recipe.id and rf.deleted = 0 and rf.food_name like '%%%s%%')", foodName))
                    .collect(Collectors.joining(" AND "));
        }
        // 处理 收藏夹 collectId 过滤条件
        String collectSql = "";
        if (!Objects.isNull(pageReqVO.getCollectId())) {
            // 关联查询收藏夹
            collectSql = String.format("exists(select 1 from meals_user_favor uf where uf.content_id = meals_recipe.id and uf.deleted = 0 and uf.collect_id = '%s')", pageReqVO.getCollectId());
        }
        // 处理 菜谱分类 recipeCategory 过滤条件
        String recipeCategorySql = "";
        if (CollUtil.isNotEmpty(pageReqVO.getRecipeCategory())) {
            recipeCategorySql = pageReqVO.getRecipeCategory().stream()
                    .map(recipeCategory -> "FIND_IN_SET(" + recipeCategory + ", recipe_category)")
                    .collect(Collectors.joining(" OR "));
        }
        // 处理 食材分类 foodCategory 过滤条件
        String foodCategorySql = "";
        if (CollUtil.isNotEmpty(pageReqVO.getFoodCategory())) {
            // 关联查询食材表
            foodCategorySql = "exists(select 1 from meals_recipe_food rf\n" +
                    "  where rf.recipe_id = meals_recipe.id\n" +
                    "    and rf.deleted = 0\n" +
                    "    and rf.food_name REGEXP (\n" +
                    "      SELECT GROUP_CONCAT(f.name SEPARATOR '|') \n" +
                    "      FROM meals_food f\n" +
                    "      where %s \n" +
                    "    )\n" +
                    ")";
            // 拼接食材类型查询条件
            foodCategorySql = String.format(foodCategorySql, pageReqVO.getFoodCategory().stream()
                    .map(foodCategory -> "FIND_IN_SET(" + foodCategory + ", f.food_category)")
                    .collect(Collectors.joining(" OR ")));
        }
        // 查询条件
        LambdaQueryWrapper<RecipeDO> queryWrapper = new LambdaQueryWrapperX<RecipeDO>()
                // 菜谱名称
                .likeIfPresent(RecipeDO::getName, pageReqVO.getName())
                .eqIfPresent(RecipeDO::getRecipeLevel, pageReqVO.getRecipeLevel())
                .eqIfPresent(RecipeDO::getStatus, pageReqVO.getStatus())
                .betweenIfPresent(RecipeDO::getCreateTime, pageReqVO.getCreateTime())
                // 组合分类筛选与可见性条件
                .and(wrapper -> wrapper
                        // 嵌套可见性OR条件组
                        .and(subWrapper -> subWrapper
                                // 情况1：系统菜谱
                                .or(orWrapper -> orWrapper
                                        .eq(RecipeDO::getRecipeType, RecipeTypesEnum.SYSTEM.getType())
                                )
                                // 情况2：当前用户菜谱
                                .or(userId != null, orWrapper -> orWrapper
                                        .eq(RecipeDO::getUserId, userId)
                                )
                                // 情况3：公开的用户菜谱
                                .or(orWrapper -> orWrapper
                                        .eq(RecipeDO::getStatus, RecipeStatusEnum.PUBLIC.getType())
                                )
                        )
                )
                // 收藏夹
                .apply(StrUtil.isNotEmpty(collectSql), collectSql)
                // 菜谱分类
                .apply(StrUtil.isNotEmpty(recipeCategorySql), recipeCategorySql)
                // 食材分类
                .apply(StrUtil.isNotEmpty(foodCategorySql), foodCategorySql)
                // 食材名称
                .apply(StrUtil.isNotEmpty(foodNameSql), foodNameSql)
                // 按照更新时间排序
                .orderByDesc(RecipeDO::getUpdateTime)
                .orderByDesc(RecipeDO::getId);
        return selectPage(pageReqVO, queryWrapper);
    }

    /**
     * 会员：分页查询菜谱
     *
     * @param userId 用户ID
     * @param reqVO  请求对象
     * @return
     */
    default PageResult<RecipeDO> selectPage(Long userId, AppRecipePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeDO>()
                .eqIfPresent(RecipeDO::getUserId, userId)
                .likeIfPresent(RecipeDO::getName, reqVO.getName())
                .eqIfPresent(RecipeDO::getRecipeType, reqVO.getRecipeType())
                .eqIfPresent(RecipeDO::getRecipeLevel, reqVO.getRecipeLevel())
                .eqIfPresent(RecipeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecipeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeDO::getId));
    }
}
