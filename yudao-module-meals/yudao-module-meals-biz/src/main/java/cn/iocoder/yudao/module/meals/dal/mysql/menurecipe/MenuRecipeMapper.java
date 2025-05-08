package cn.iocoder.yudao.module.meals.dal.mysql.menurecipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo.MenuRecipePageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * 菜单菜谱 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface MenuRecipeMapper extends BaseMapperX<MenuRecipeDO> {
    /**
     * 获得菜单菜谱
     * @param id 编号
     * @return 菜单菜谱
     */
    @Override
    default MenuRecipeDO selectById(Serializable id) {
        // 获取详细信息
        return this.selectJoinOne(MenuRecipeDO.class, new MPJLambdaWrapperX<MenuRecipeDO>()
                // 查询所有真字段
                .selectAll(MenuRecipeDO.class)
                // 菜单菜谱ID匹配
                .eq(MenuRecipeDO::getId, id)
                // 菜谱名称
                .selectAs(RecipeDO::getName, MenuRecipeDO::getRecipeName)
                // 菜谱图片
                .selectAs(RecipeDO::getPicUrl, MenuRecipeDO::getRecipePicUrl)
                // 关联
                .leftJoin(RecipeDO.class, RecipeDO::getId, MenuRecipeDO::getRecipeId));
    }

    default PageResult<MenuRecipeDO> selectPage(MenuRecipePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MenuRecipeDO>()
                .eqIfPresent(MenuRecipeDO::getRecipeMenuId, reqVO.getRecipeMenuId())
                .eqIfPresent(MenuRecipeDO::getMemo, reqVO.getMemo())
                .betweenIfPresent(MenuRecipeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MenuRecipeDO::getId));
    }

    /**
     * 根据菜单ID获取对应的菜谱
     * @param reqVO
     * @param recipeMenuId
     * @return
     */
    default PageResult<MenuRecipeDO> selectPage(MenuRecipePageReqVO reqVO, Long recipeMenuId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MenuRecipeDO>()
                .eq(MenuRecipeDO::getRecipeMenuId, recipeMenuId)
                .orderByDesc(MenuRecipeDO::getId));
    }

    /**
     * 根据菜单ID，删除对应的菜单菜谱
     * @param recipeMenuId
     * @return
     */
    default int deleteByRecipeMenuId(Long recipeMenuId) {
        return delete(MenuRecipeDO::getRecipeMenuId, recipeMenuId);
    }

}
