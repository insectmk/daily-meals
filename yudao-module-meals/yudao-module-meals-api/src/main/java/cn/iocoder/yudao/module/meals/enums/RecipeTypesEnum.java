package cn.iocoder.yudao.module.meals.enums;

import cn.hutool.core.util.ObjUtil;
import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

/**
 * 菜谱类型枚举
 *
 * @author InsectMk
 */
@Getter
@AllArgsConstructor
public enum RecipeTypesEnum implements ArrayValuable<Integer> {

    SYSTEM(0, "系统菜谱"),
    USER(1, "用户菜谱");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RecipeTypesEnum::getType).toArray(Integer[]::new);

    /**
     * 类型值
     */
    private final Integer type;
    /**
     * 类型名
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    /**
     * 判断是否为系统菜谱
     * @param type 菜谱类型值
     * @return 是否
     */
    public static boolean isSystem(Integer type) {
        return ObjUtil.equal(SYSTEM.type, type);
    }

    /**
     * 判断是否为用户菜谱
     * @param type 菜谱类型值
     * @return 是否
     */
    public static boolean isUser(Integer type) {
        return ObjUtil.equal(USER.type, type);
    }

}
