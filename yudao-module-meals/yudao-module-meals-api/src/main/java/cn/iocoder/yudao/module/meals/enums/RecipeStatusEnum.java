package cn.iocoder.yudao.module.meals.enums;

import cn.hutool.core.util.ObjUtil;
import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

/**
 * 菜谱状态枚举
 *
 * @author InsectMk
 */
@Getter
@AllArgsConstructor
public enum RecipeStatusEnum implements ArrayValuable<DictDataRespDTO> {

    PUBLIC(0, "公开"),
    PRIVATE(1, "私有");

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

    /**
     * 判断是否公开
     * @param type 菜谱状态值
     * @return 是否
     */
    public static boolean isPublic(Integer type) {
        return ObjUtil.equal(PUBLIC.type, type);
    }

    /**
     * 判断是否私有
     * @param type 菜谱状态值
     * @return 是否
     */
    public static boolean isPrivate(Integer type) {
        return ObjUtil.equal(PRIVATE.type, type);
    }

}
