package cn.iocoder.yudao.module.meals.dal.mysql.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipePageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDetailDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

/**
 * 菜谱 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface RecipeMapper extends BaseMapperX<RecipeDO> {
    default PageResult<RecipeDO> selectPage(RecipePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeDO>()
                .likeIfPresent(RecipeDO::getName, reqVO.getName())
                .eqIfPresent(RecipeDO::getRecipeType, reqVO.getRecipeType())
                .eqIfPresent(RecipeDO::getRecipeLevel, reqVO.getRecipeLevel())
                .eqIfPresent(RecipeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecipeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeDO::getId));
    }

    /**
     * 会员：分页查询菜谱
     *
     * @param userId 用户ID
     * @param reqVO  请求对象
     * @return
     */
    default PageResult<RecipeDO> selectPage(Long userId, AppRecipePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeDO>()
                .eqIfPresent(RecipeDO::getUserId, userId)
                .likeIfPresent(RecipeDO::getName, reqVO.getName())
                .eqIfPresent(RecipeDO::getRecipeType, reqVO.getRecipeType())
                .eqIfPresent(RecipeDO::getRecipeLevel, reqVO.getRecipeLevel())
                .eqIfPresent(RecipeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecipeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeDO::getId));
    }
}
