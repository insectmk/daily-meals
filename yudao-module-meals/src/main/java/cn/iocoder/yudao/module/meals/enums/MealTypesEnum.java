package cn.iocoder.yudao.module.meals.enums;

import cn.iocoder.yudao.framework.common.biz.system.dict.dto.DictDataRespDTO;
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
public enum MealTypesEnum implements ArrayValuable<DictDataRespDTO> {
    BREAKFAST(0, "早餐"),
    LUNCH(1, "午餐"),
    DINNER(2, "晚餐");

    public static final DictDataRespDTO[] ARRAYS = Arrays.stream(values())
            .map(e -> {
                DictDataRespDTO dictDataRespDTO = new DictDataRespDTO();
                dictDataRespDTO.setValue(e.getType().toString());
                dictDataRespDTO.setLabel(e.getName());
                return dictDataRespDTO;
            }) // 使用枚举的type和label创建DTO实例
            .toArray(DictDataRespDTO[]::new);

    /**
     * 类型值
     */
    private final Integer type;
    /**
     * 类型名
     */
    private final String name;

    @Override
    public DictDataRespDTO[] array() {
        return ARRAYS;
    }

}
