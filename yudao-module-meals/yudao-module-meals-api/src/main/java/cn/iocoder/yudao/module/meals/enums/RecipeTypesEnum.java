package cn.iocoder.yudao.module.meals.enums;

import cn.hutool.core.util.ObjUtil;
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
public enum RecipeTypesEnum implements ArrayValuable<DictDataRespDTO> {

    SYSTEM(0, "系统菜谱"),
    USER(1, "用户菜谱");

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
