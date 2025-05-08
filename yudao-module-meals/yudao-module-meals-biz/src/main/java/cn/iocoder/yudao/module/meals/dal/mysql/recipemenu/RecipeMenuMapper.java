package cn.iocoder.yudao.module.meals.dal.mysql.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.RecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.module.meals.enums.RecipeStatusEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜谱菜单 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface RecipeMenuMapper extends BaseMapperX<RecipeMenuDO> {

    default PageResult<RecipeMenuDO> selectPage(RecipeMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeMenuDO>()
                .likeIfPresent(RecipeMenuDO::getTitle, reqVO.getTitle())
                .likeIfPresent(RecipeMenuDO::getSubtitle, reqVO.getSubtitle())
                .betweenIfPresent(RecipeMenuDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeMenuDO::getId));
    }

    /**
     * 获得用户可见菜谱菜单分页
     * @param userId 用户ID
     * @param reqVO 分页请求
     * @return 用户可见的菜谱菜单分页信息
     */
    default PageResult<RecipeMenuDO> selectUserViewablePage(Long userId, AppRecipeMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeMenuDO>()
                .likeIfPresent(RecipeMenuDO::getTitle, reqVO.getTitle())
                .likeIfPresent(RecipeMenuDO::getSubtitle, reqVO.getSubtitle())
                .betweenIfPresent(RecipeMenuDO::getCreateTime, reqVO.getCreateTime())
                /*
                 * 组合可见性条件，可见为，三种情况
                 * 1. 该用户的菜单
                 * 2. 其他用户公开的菜单
                 * 3. 系统的菜单
                 * */
                .and(wrapper -> wrapper
                        // 嵌套可见性OR条件组
                        .and(subWrapper -> subWrapper
                                // 情况1：当前用户菜单
                                .or(userId != null,orWrapper -> orWrapper
                                        .eq(RecipeMenuDO::getUserId, userId)
                                )
                                // 情况2：公开的用户菜单
                                .or(orWrapper -> orWrapper
                                        .eq(RecipeMenuDO::getMenuStatus, RecipeStatusEnum.PUBLIC.getType())
                                )
                                // 情况3：系统菜单
                                .or(orWrapper -> orWrapper
                                        .eq(RecipeMenuDO::getMenuType, RecipeTypesEnum.SYSTEM.getType())
                                )
                        )
                )
                .orderByDesc(RecipeMenuDO::getId));
    }
}
