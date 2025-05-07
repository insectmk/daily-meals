package cn.iocoder.yudao.module.meals.dal.mysql.menu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.menu.vo.MenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.menu.vo.AppMenuPageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menu.MenuDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单 Mapper
 *
 * @author 珍珍
 */
@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    default PageResult<MenuDO> selectPage(MenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MenuDO>()
                .eqIfPresent(MenuDO::getTitle, reqVO.getTitle())
                .eqIfPresent(MenuDO::getSubtitle, reqVO.getSubtitle())
                .eqIfPresent(MenuDO::getMenuType, reqVO.getMenuType())
                .eqIfPresent(MenuDO::getMenuStatus, reqVO.getMenuStatus())
                .betweenIfPresent(MenuDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MenuDO::getId));
    }

    /**
     * 获取用户可见的菜单分页
     * @param userId 用户ID
     * @param reqVO 分页请求
     * @return 用户可见的菜单内容集合
     */
    default PageResult<MenuDO> selectUserViewablePage(Long userId, AppMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MenuDO>()
                /**
                 * todo 三种情况
                 *
                 * 1. 该用户的
                 * 2. 系统的
                 * 3. 其他用户的，公开的
                 */
                .eqIfPresent(MenuDO::getTitle, reqVO.getTitle())
                .eqIfPresent(MenuDO::getSubtitle, reqVO.getSubtitle())
                .eqIfPresent(MenuDO::getMenuType, reqVO.getMenuType())
                .eqIfPresent(MenuDO::getMenuStatus, reqVO.getMenuStatus())
                .betweenIfPresent(MenuDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MenuDO::getId));
    }

}
