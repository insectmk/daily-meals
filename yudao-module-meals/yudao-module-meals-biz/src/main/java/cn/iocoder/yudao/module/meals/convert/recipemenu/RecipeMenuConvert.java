package cn.iocoder.yudao.module.meals.convert.recipemenu;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.food.vo.FoodSimpleRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSimpleRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Title: RecipeMenuConvert
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.convert.recipemenu
 * @Date 2025/5/8 17:00
 * @description: 菜谱菜单对象转换
 */
@Mapper
public interface RecipeMenuConvert {
    RecipeMenuConvert INSTANCE = Mappers.getMapper(RecipeMenuConvert.class);

    /**
     * 将菜谱菜单信息转为菜谱菜单精简信息列表
     * @param list
     * @return
     */
    default List<AppRecipeMenuSimpleRespVO> convertSimpleList(List<RecipeMenuDO> list) {
        return BeanUtils.toBean(list, AppRecipeMenuSimpleRespVO.class);
    }
}
