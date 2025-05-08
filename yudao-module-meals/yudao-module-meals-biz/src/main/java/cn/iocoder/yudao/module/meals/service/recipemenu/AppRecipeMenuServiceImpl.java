package cn.iocoder.yudao.module.meals.service.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSimpleRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.module.meals.dal.mysql.recipemenu.RecipeMenuMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.RECIPE_MENU_NOT_EXISTS;

/**
 * 菜谱菜单 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppRecipeMenuServiceImpl implements AppRecipeMenuService {

    @Resource
    private RecipeMenuMapper recipeMenuMapper;

    @Override
    public Long createRecipeMenu(AppRecipeMenuSaveReqVO createReqVO) {
        // 插入
        RecipeMenuDO recipeMenu = BeanUtils.toBean(createReqVO, RecipeMenuDO.class);
        recipeMenuMapper.insert(recipeMenu);
        // 返回
        return recipeMenu.getId();
    }

    @Override
    public void updateRecipeMenu(AppRecipeMenuSaveReqVO updateReqVO) {
        // 校验存在
        validateRecipeMenuExists(updateReqVO.getId());
        // 更新
        RecipeMenuDO updateObj = BeanUtils.toBean(updateReqVO, RecipeMenuDO.class);
        recipeMenuMapper.updateById(updateObj);
    }

    @Override
    public void deleteRecipeMenu(Long id) {
        // 校验存在
        validateRecipeMenuExists(id);
        // 删除
        recipeMenuMapper.deleteById(id);
    }

    private void validateRecipeMenuExists(Long id) {
        if (recipeMenuMapper.selectById(id) == null) {
            throw exception(RECIPE_MENU_NOT_EXISTS);
        }
    }

    @Override
    public RecipeMenuDO getRecipeMenu(Long id) {
        return recipeMenuMapper.selectById(id);
    }

    @Override
    public PageResult<RecipeMenuDO> getUserViewableRecipeMenuPage(Long userId, AppRecipeMenuPageReqVO pageReqVO) {
        return recipeMenuMapper.selectUserViewablePage(userId, pageReqVO);
    }

    @Override
    public List<RecipeMenuDO> getSelfRecipeMenuList(Long userId) {
        return recipeMenuMapper.selectList(new LambdaQueryWrapperX<RecipeMenuDO>()
                .eq(RecipeMenuDO::getUserId, userId));
    }

}
