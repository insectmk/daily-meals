package cn.iocoder.yudao.module.meals.dal.mysql.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.recipemenu.vo.RecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
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
                /*
                 * todo 可见为，三种情况
                 * 1. 该用户的菜单
                 * 2. 其他用户公开的菜单
                 * 3. 系统的菜单
                 * */
                .likeIfPresent(RecipeMenuDO::getTitle, reqVO.getTitle())
                .likeIfPresent(RecipeMenuDO::getSubtitle, reqVO.getSubtitle())
                .betweenIfPresent(RecipeMenuDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeMenuDO::getId));
    }
}
