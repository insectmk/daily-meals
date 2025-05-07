package cn.iocoder.yudao.module.meals.service.menu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.menu.vo.MenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.menu.vo.AppMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.menu.vo.AppMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menu.MenuDO;
import cn.iocoder.yudao.module.meals.dal.mysql.menu.MenuMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.MENU_NOT_EXISTS;

/**
 * 菜单 Service 实现类
 *
 * @author 珍珍
 */
@Service
@Validated
public class AppMenuServiceImpl implements AppMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public Long createMenu(AppMenuSaveReqVO createReqVO) {
        // 插入
        MenuDO menu = BeanUtils.toBean(createReqVO, MenuDO.class);
        menuMapper.insert(menu);
        // 返回
        return menu.getId();
    }

    @Override
    public void updateMenu(AppMenuSaveReqVO updateReqVO) {
        // 校验存在
        validateMenuExists(updateReqVO.getId());
        // 更新
        MenuDO updateObj = BeanUtils.toBean(updateReqVO, MenuDO.class);
        menuMapper.updateById(updateObj);
    }

    @Override
    public void deleteMenu(Long id) {
        // 校验存在
        validateMenuExists(id);
        // 删除
        menuMapper.deleteById(id);
    }

    private void validateMenuExists(Long id) {
        if (menuMapper.selectById(id) == null) {
            throw exception(MENU_NOT_EXISTS);
        }
    }

    @Override
    public MenuDO getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public PageResult<MenuDO> getMenuPage(Long userId, AppMenuPageReqVO pageReqVO) {
        return menuMapper.selectUserViewablePage(userId, pageReqVO);
    }

}
