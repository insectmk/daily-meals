package cn.iocoder.yudao.module.meals.enums;

/**
 * Meals 字典类型的枚举类
 *
 * @author InsectMk
 */
public interface DictTypeConstants {
    // *********数据库字典**********
    String MEALS_RECIPE_TYPE = "meals_recipe_type"; // 菜谱类型
    String MEALS_RECIPE_TAG = "meals_recipe_tag"; // 菜谱标签
    String MEALS_RECIPE_LEVEL = "meals_recipe_level"; // 菜谱烹饪难度
    String MEALS_FOOD_TYPE = "meals_food_type"; // 食材分类
    String MEALS_FOOD_UNIT = "meals_food_unit"; // 食材单位
    String MEALS_MEAL_TYPE = "meals_meal_type"; // 餐次类型

    // *********静态字典**********
    String MEALS_STATIC_RECIPE_STATUS = "meals_static_recipe_status"; // 菜谱状态
    String MEALS_STATIC_RECIPE_TYPES = "meals_static_recipe_types"; // 菜谱类型
    String MEALS_STATIC_CONTENT_TYPES = "meals_static_content_types"; // 内容类型枚举
    String MEALS_STATIC_TRUE_FALSE = "meals_static_true_false"; // 是否枚举
    String MEALS_STATIC_MEAL_TYPES = "meals_static_meal_types"; // 餐次类型枚举
}
