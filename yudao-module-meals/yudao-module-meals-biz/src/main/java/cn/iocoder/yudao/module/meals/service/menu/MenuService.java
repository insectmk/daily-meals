package cn.iocoder.yudao.module.meals.service.menu;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.menu.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.menu.MenuDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 菜单 Service 接口
 *
 * @author 珍珍
 */
public interface MenuService {

    /**
     * 创建菜单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMenu(@Valid MenuSaveReqVO createReqVO);

    /**
     * 更新菜单
     *
     * @param updateReqVO 更新信息
     */
    void updateMenu(@Valid MenuSaveReqVO updateReqVO);

    /**
     * 删除菜单
     *
     * @param id 编号
     */
    void deleteMenu(Long id);

    /**
     * 获得菜单
     *
     * @param id 编号
     * @return 菜单
     */
    MenuDO getMenu(Long id);

    /**
     * 获得菜单分页
     *
     * @param pageReqVO 分页查询
     * @return 菜单分页
     */
    PageResult<MenuDO> getMenuPage(MenuPageReqVO pageReqVO);

}