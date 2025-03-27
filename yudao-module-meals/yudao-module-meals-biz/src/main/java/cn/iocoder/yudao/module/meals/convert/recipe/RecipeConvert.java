package cn.iocoder.yudao.module.meals.convert.recipe;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Title: ReceipeConvert
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.convert.recipe
 * @Date 2025/3/21 10:05
 * @description: 食谱对象转换
 */
@Mapper
public interface RecipeConvert {
    RecipeConvert INSTANCE = Mappers.getMapper(RecipeConvert.class);

}
