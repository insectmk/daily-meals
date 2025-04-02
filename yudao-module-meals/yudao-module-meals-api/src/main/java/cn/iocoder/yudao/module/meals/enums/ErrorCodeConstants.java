package cn.iocoder.yudao.module.meals.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Meals 错误码枚举类
 * Meals 每日饭菜，使用 1-001-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 菜谱 ==========
    ErrorCode RECIPE_NOT_EXISTS = new ErrorCode(100_000_001, "菜谱不存在");
    // ========== 食材 ==========
    ErrorCode FOOD_NOT_EXISTS = new ErrorCode(200_000_001, "食材不存在");
    // ========== 菜谱计划 ==========
    ErrorCode DAILY_PLAN_NOT_EXISTS = new ErrorCode(300_000_001, "菜谱计划不存在");
    ErrorCode DAILY_PLAN_ALREADY_EXISTS = new ErrorCode(300_000_002, "菜谱计划已存在");

    // ========== 菜谱食材 ==========
    ErrorCode RECIPE_FOOD_NOT_EXISTS = new ErrorCode(400_000_001, "菜谱食材不存在");
}
