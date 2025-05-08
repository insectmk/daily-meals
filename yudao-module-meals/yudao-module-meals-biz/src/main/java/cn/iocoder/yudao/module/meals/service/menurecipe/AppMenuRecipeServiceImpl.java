package cn.iocoder.yudao.module.meals.service.menurecipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.common.util.object.ObjectUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.menurecipe.vo.MenuRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.menurecipe.vo.AppMenuRecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.menurecipe.vo.AppMenuRecipeSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.module.meals.dal.mysql.menurecipe.MenuRecipeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.MENU_RECIPE_ALREADY_EXISTS;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.MENU_RECIPE_NOT_EXISTS;

/**
 * 菜单菜谱 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppMenuRecipeServiceImpl implements AppMenuRecipeService {

    @Resource
    private MenuRecipeMapper menuRecipeMapper;

    @Override
    public Long createMenuRecipe(AppMenuRecipeSaveReqVO createReqVO) {
        // 判断是否已经存在
        if (menuRecipeMapper.selectCount(new LambdaQueryWrapperX<MenuRecipeDO>()
                .eqIfPresent(MenuRecipeDO::getRecipeId, createReqVO.getRecipeId())
                .eqIfPresent(MenuRecipeDO::getRecipeMenuId, createReqVO.getRecipeMenuId())) > 0) {
            // 存在则报错
            throw exception(MENU_RECIPE_ALREADY_EXISTS);
        }
        // 插入
        MenuRecipeDO menuRecipe = BeanUtils.toBean(createReqVO, MenuRecipeDO.class);
        menuRecipeMapper.insert(menuRecipe);
        // 返回
        return menuRecipe.getId();
    }

    @Override
    public void updateMenuRecipe(AppMenuRecipeSaveReqVO updateReqVO) {
        // 校验存在
        validateMenuRecipeExists(updateReqVO.getId());
        // 更新
        MenuRecipeDO updateObj = BeanUtils.toBean(updateReqVO, MenuRecipeDO.class);
        menuRecipeMapper.updateById(updateObj);
    }

    @Override
    public void deleteMenuRecipe(Long id) {
        // 校验存在
        validateMenuRecipeExists(id);
        // 删除
        menuRecipeMapper.deleteById(id);
    }

    private void validateMenuRecipeExists(Long id) {
        if (menuRecipeMapper.selectById(id) == null) {
            throw exception(MENU_RECIPE_NOT_EXISTS);
        }
    }

    @Override
    public MenuRecipeDO getMenuRecipe(Long id) {
        return menuRecipeMapper.selectById(id);
    }

    @Override
    public PageResult<MenuRecipeDO> getMenuRecipePage(AppMenuRecipePageReqVO pageReqVO) {
        return menuRecipeMapper.selectPage(BeanUtils.toBean(pageReqVO, MenuRecipePageReqVO.class));
    }

}
