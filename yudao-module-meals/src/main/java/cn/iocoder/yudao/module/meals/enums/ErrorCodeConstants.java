package cn.iocoder.yudao.module.meals.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Meals 错误码枚举类
 * Meals 每日饭菜，使用 1-001-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 菜谱 ==========
    ErrorCode RECIPE_NOT_EXISTS = new ErrorCode(200_000_001, "菜谱不存在");
    // ========== 食材 ==========
    ErrorCode FOOD_NOT_EXISTS = new ErrorCode(200_002_001, "食材不存在");
    // ========== 菜谱计划 ==========
    ErrorCode DAILY_PLAN_NOT_EXISTS = new ErrorCode(200_003_001, "菜谱计划不存在");
    ErrorCode DAILY_PLAN_ALREADY_EXISTS = new ErrorCode(200_003_002, "菜谱计划已存在");

    // ========== 每日计划明细 ==========
    ErrorCode DAILY_PLAN_ITEM_NOT_EXISTS = new ErrorCode(200_004_001, "每日计划明细不存在");

    // ========== 菜谱食材 ==========
    ErrorCode RECIPE_FOOD_NOT_EXISTS = new ErrorCode(200_0005_001, "菜谱食材不存在");

    // ========== 菜谱分类 ==========
    ErrorCode RECIPE_CATEGORY_NOT_EXISTS = new ErrorCode(200_006_001, "菜谱分类不存在");
    ErrorCode RECIPE_CATEGORY_EXITS_CHILDREN = new ErrorCode(200_006_002, "存在存在子菜谱分类，无法删除");
    ErrorCode RECIPE_CATEGORY_PARENT_NOT_EXITS = new ErrorCode(200_006_003,"父级菜谱分类不存在");
    ErrorCode RECIPE_CATEGORY_PARENT_ERROR = new ErrorCode(200_006_004, "不能设置自己为父菜谱分类");
    ErrorCode RECIPE_CATEGORY_NAME_DUPLICATE = new ErrorCode(200_006_005, "已经存在该分类名称的菜谱分类");
    ErrorCode RECIPE_CATEGORY_PARENT_IS_CHILD = new ErrorCode(200_006_006, "不能设置自己的子RecipeCategory为父RecipeCategory");

    // ========== 食材分类 ==========
    ErrorCode FOOD_CATEGORY_NOT_EXISTS = new ErrorCode(200_007_001, "食材分类不存在");
    ErrorCode FOOD_CATEGORY_EXITS_CHILDREN = new ErrorCode(200_007_002, "存在存在子食材分类，无法删除");
    ErrorCode FOOD_CATEGORY_PARENT_NOT_EXITS = new ErrorCode(200_007_003,"父级食材分类不存在");
    ErrorCode FOOD_CATEGORY_PARENT_ERROR = new ErrorCode(200_007_004, "不能设置自己为父食材分类");
    ErrorCode FOOD_CATEGORY_NAME_DUPLICATE = new ErrorCode(200_007_005, "已经存在该分类名称的食材分类");
    ErrorCode FOOD_CATEGORY_PARENT_IS_CHILD = new ErrorCode(200_007_006, "不能设置自己的子FoodCategory为父FoodCategory");

    // ========== 菜谱菜单 ==========
    ErrorCode RECIPE_MENU_NOT_EXISTS = new ErrorCode(200_008_001, "菜单不存在");

    // ========== 菜单菜谱 ==========
    ErrorCode MENU_RECIPE_NOT_EXISTS = new ErrorCode(200_009_001, "菜单菜谱不存在");
    ErrorCode MENU_RECIPE_ALREADY_EXISTS = new ErrorCode(200_009_002, "菜单菜谱已存在");

    // ========== 用户收藏夹 ==========
    ErrorCode USER_COLLECT_NOT_EXISTS = new ErrorCode(200_010_001, "用户收藏夹不存在");

    // ========== 用户收藏 ==========
    ErrorCode USER_FAVOR_NOT_EXISTS = new ErrorCode(200_011_001, "用户收藏不存在");
    ErrorCode USER_FAVOR_ALREADY_CANCEL = new ErrorCode(200_011_002, "已取消收藏");

    // ========== 评论 ==========
    ErrorCode COMMENT_NOT_EXISTS = new ErrorCode(200_012_001, "评论不存在");
}
