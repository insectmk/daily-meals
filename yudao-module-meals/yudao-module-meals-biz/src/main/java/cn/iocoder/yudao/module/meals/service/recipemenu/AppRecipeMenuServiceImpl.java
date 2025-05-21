package cn.iocoder.yudao.module.meals.service.recipemenu;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo.AppRecipeMenuSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.menurecipe.MenuRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipemenu.RecipeMenuDO;
import cn.iocoder.yudao.module.meals.dal.mysql.menurecipe.MenuRecipeMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipemenu.RecipeMenuMapper;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import cn.iocoder.yudao.module.meals.service.recipe.AppRecipeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.RECIPE_MENU_NOT_EXISTS;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.RECIPE_NOT_EXISTS;

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
    @Resource
    private AppRecipeService appRecipeService;
    @Resource
    private MenuRecipeMapper menuRecipeMapper;

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
    public PageResult<RecipeMenuDO> getUserSelfRecipeMenuPage(Long userId, AppRecipeMenuPageReqVO pageReqVO) {
        return recipeMenuMapper.selectUserSelfPage(userId, pageReqVO);
    }

    @Override
    public List<RecipeMenuDO> getSelfRecipeMenuList(Long userId) {
        return recipeMenuMapper.selectList(new LambdaQueryWrapperX<RecipeMenuDO>()
                .eq(RecipeMenuDO::getUserId, userId));
    }

    @Override
    public AppRecipeMenuRespVO getRecipeMenuDetail(Long userId, Long id) {
        RecipeMenuDO recipeMenuDO = recipeMenuMapper.selectById(id);
        // 查询菜谱菜单基础信息
        AppRecipeMenuRespVO result = BeanUtils.toBean(recipeMenuDO, AppRecipeMenuRespVO.class);
        if (Objects.isNull(result)) {
            // 菜谱菜单不存在
            throw exception(RECIPE_MENU_NOT_EXISTS);
        }
        // 判断是否为用户的菜谱菜单
        result.setSelfMenu(!Objects.isNull(userId)
                && Objects.equals(userId, recipeMenuDO.getUserId())
                && RecipeTypesEnum.USER.getType().equals(recipeMenuDO.getMenuType()));
        // 查询菜单菜谱信息
        List<MenuRecipeDO> menuRecipeDOS = menuRecipeMapper.selectList(new LambdaQueryWrapperX<MenuRecipeDO>()
                .eqIfPresent(MenuRecipeDO::getRecipeMenuId, id));
        // 查询并装载菜谱信息
        List<AppRecipeRespVO> recipes = new ArrayList<>(menuRecipeDOS.size());
        for (MenuRecipeDO menuRecipeDO : menuRecipeDOS) {
            recipes.add(appRecipeService.getRecipeDetail(userId, menuRecipeDO.getRecipeId()));
        }
        result.setRecipes(recipes);

        return result;
    }

    @Override
    public Long createOrUpdateRecipe(Long userId, AppRecipeMenuSaveReqVO createReqVO) {
        Long recipeMenuId = createReqVO.getId();
        // 根据id判断新增还是修改
        if (Objects.isNull(recipeMenuId)) {
            // 为空：新增
            createReqVO.setUserId(userId); // 设置用户ID
            return this.createRecipeMenu(createReqVO);
        }
        // 更新菜谱菜单
        this.updateRecipeMenu(createReqVO);
        // 返回
        return recipeMenuId;
    }

    @Override
    public void deleteRecipeMenu(Long userId, Long id) {
        // 查看菜谱是否为用户菜谱
        if ( recipeMenuMapper.selectOne(new LambdaQueryWrapperX<RecipeMenuDO>()
                // 该用户
                .eq(RecipeMenuDO::getUserId, userId)
                // 类型为：用户菜谱菜单
                .eq(RecipeMenuDO::getMenuType, RecipeTypesEnum.USER.getType())
                // 该菜谱菜单
                .eq(RecipeMenuDO::getId, id)) == null) {
            // 菜谱不存在
            throw exception(RECIPE_MENU_NOT_EXISTS);
        }
        // 删除菜谱
        recipeMenuMapper.deleteById(id);
        // 删除菜单的菜谱信息
        menuRecipeMapper.deleteByRecipeMenuId(id);
    }
}
