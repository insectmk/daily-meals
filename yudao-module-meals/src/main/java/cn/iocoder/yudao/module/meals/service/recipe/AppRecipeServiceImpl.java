package cn.iocoder.yudao.module.meals.service.recipe;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.*;
import cn.iocoder.yudao.module.meals.convert.recipe.RecipeConvert;
import cn.iocoder.yudao.module.meals.dal.dataobject.dailyplanitem.PopularPublicRecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercomment.UserCommentDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.dal.mysql.dailyplanitem.DailyPlanItemMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeFoodMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.recipe.RecipeMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.usercomment.UserCommentMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.userfavor.UserFavorMapper;
import cn.iocoder.yudao.module.meals.enums.ContentTypesEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeStatusEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.RECIPE_NOT_EXISTS;

/**
 * APP菜谱 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppRecipeServiceImpl implements AppRecipeService {

    @Resource
    private RecipeMapper recipeMapper;
    @Resource
    private RecipeFoodMapper recipeFoodMapper;
    @Resource
    private DailyPlanItemMapper dailyPlanItemMapper;
    @Resource
    private UserFavorMapper userFavorMapper;
    @Resource
    private UserCommentMapper userCommentMapper;
    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public AppRecipeRespVO getRecipeDetail(Long userId, Long id) {
        // 查询基础信息并判断是否在今日计划中
        RecipeDO recipeDO = recipeMapper.selectById(id);
        if (recipeDO == null) {
            return null;
        }
        AppRecipeRespVO appRecipeRespVO = BeanUtils.toBean(recipeDO, AppRecipeRespVO.class);
        // 判断是否是用户自己的菜谱
        appRecipeRespVO.setSelfRecipe(!Objects.isNull(userId)
                && Objects.equals(userId, recipeDO.getUserId())
                && RecipeTypesEnum.USER.getType().equals(recipeDO.getRecipeType()));
        // 判断是否收藏
        appRecipeRespVO.setFavor(userFavorMapper.exists(new LambdaQueryWrapperX<UserFavorDO>()
                .eq(UserFavorDO::getUserId, userId) // 该用户
                .eq(UserFavorDO::getContentType, ContentTypesEnum.RECIPE.getType()) // 菜谱内容
                .eq(UserFavorDO::getContentId, id) // 内容ID为菜谱ID
        ));
        // 查询食材信息
        List<RecipeFoodDO> recipeFoods = recipeFoodMapper.selectList(new LambdaQueryWrapperX<RecipeFoodDO>()
                .eqIfPresent(RecipeFoodDO::getRecipeId, id));
        // 拼装食材信息
        appRecipeRespVO.setFoods(BeanUtils.toBean(recipeFoods, AppRecipeFoodRespVO.class));
        // 查询用户信息
        if (RecipeTypesEnum.USER.getType().equals(recipeDO.getRecipeType())) {
            MemberUserRespDTO user = memberUserApi.getUser(recipeDO.getUserId());
            appRecipeRespVO.setUserNickname(user.getNickname()); // 用户昵称
            appRecipeRespVO.setUserAvatar(user.getAvatar()); // 用户头像
            // 查询是否关注菜谱作者
            appRecipeRespVO.setUserFavor(userFavorMapper.exists(new LambdaQueryWrapperX<UserFavorDO>()
                    .eq(UserFavorDO::getUserId, userId) // 当前登录用户的关注
                    .eq(UserFavorDO::getContentType, ContentTypesEnum.USER.getType()) // 内容类型为用户
                    .eq(UserFavorDO::getContentId, user.getId()) // 关注内容ID为菜谱作者ID
            ));
        }
        return appRecipeRespVO;
    }

    @Override
    public PageResult<AppRecipeRespVO> getSelfRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        pageReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 获取用户菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getSystemRecipeDetailPage(AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        RecipePageReqVO pageReqVo1 = BeanUtils.toBean(pageReqVO, RecipePageReqVO.class);
        pageReqVo1.setRecipeType(RecipeTypesEnum.SYSTEM.getType()); // 获取系统菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(pageReqVo1);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getPublicRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        pageReqVO.setStatus(RecipeStatusEnum.PUBLIC.getType()); // 公开的菜谱
        pageReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 用户菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getRecipeDetailPage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        PageResult<RecipeDO> pageResult = recipeMapper.getUserViewableRecipePage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public List<AppRecipeRespVO> getPopularPublicRecipesDetail(AppRecipePopularPublicReqVO reqVO) {
        // 查询被加入计划最多的前len位菜谱
        List<PopularPublicRecipeDO> popularPublicRecipeDOS = dailyPlanItemMapper.selectPopularPublicRecipeList(reqVO);
        // 菜谱ID集合
        Set<Long> recipeIds = convertSet(popularPublicRecipeDOS, PopularPublicRecipeDO::getRecipeId);
        // 如果没有菜谱就返回空集合
        if (recipeIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<RecipeDO> recipeList = recipeMapper.selectList(new LambdaQueryWrapperX<RecipeDO>()
                .in(RecipeDO::getId, recipeIds));
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(recipeIds);
        // 装载信息
        return RecipeConvert.INSTANCE.convertList(recipeList,recipeFoods);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRecipe(AppRecipeSaveReqVO createReqVO) {
        createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 设置状态
        createReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 创建为用户类型菜谱
        // 插入基础数据
        RecipeDO recipe = BeanUtils.toBean(createReqVO, RecipeDO.class);
        recipeMapper.insert(recipe);
        Long recipeId = recipe.getId();
        // 插入菜谱食材数据
        for (AppRecipeFoodSaveReqVO recipeFood : createReqVO.getFoods()) {
            RecipeFoodDO recipeFoodDO = BeanUtils.toBean(recipeFood, RecipeFoodDO.class);
            recipeFoodDO.setRecipeId(recipeId); // 设置菜谱ID
            recipeFoodMapper.insert(recipeFoodDO);
        }
        // 返回
        return recipeId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrUpdateRecipe(Long userId, AppRecipeSaveReqVO createReqVO) {
        Long recipeId = createReqVO.getId();
        // 根据id判断新增还是修改
        if (Objects.isNull(recipeId)) {
            // 为空：新增
            createReqVO.setUserId(userId); // 设置用户ID
            return this.createRecipe(createReqVO);
        }
        // 更新菜谱
        // 1. 更新基础数据
        validateRecipeExists(createReqVO.getId()); // 校验存在
        RecipeDO updateObj = BeanUtils.toBean(createReqVO, RecipeDO.class); // 更新
        recipeMapper.updateById(updateObj);
        // 2. 清空菜谱食材数据
        recipeFoodMapper.deleteByRecipeId(recipeId);
        // 3. 插入菜谱食材数据
        // 插入菜谱食材数据
        for (AppRecipeFoodSaveReqVO recipeFood : createReqVO.getFoods()) {
            RecipeFoodDO recipeFoodDO = BeanUtils.toBean(recipeFood, RecipeFoodDO.class);
            recipeFoodDO.setRecipeId(recipeId); // 设置菜谱ID
            recipeFoodDO.setId(null); // 清空ID，防止主键冲突
            recipeFoodMapper.insert(recipeFoodDO);
        }
        // 返回
        return recipeId;
    }

    @Override
    public void deleteRecipe(Long userId, Long id) {
        // 查看菜谱是否为用户菜谱
        if ( recipeMapper.selectOne(new LambdaQueryWrapperX<RecipeDO>()
                // 该用户
                .eq(RecipeDO::getUserId, userId)
                // 类型为：用户菜谱
                .eq(RecipeDO::getRecipeType, RecipeTypesEnum.USER.getType())
                // 该菜谱
                .eq(RecipeDO::getId, id)) == null) {
            // 菜谱不存在
            throw exception(RECIPE_NOT_EXISTS);
        }
        // 删除菜谱
        recipeMapper.deleteById(id);
    }

    @Override
    public PageResult<AppRecipeRespVO> getSelfRecipeDetailPageByCollect(Long userId, AppRecipePageReqVO pageReqVO) {
        // 查询基础信息
        pageReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 获取用户菜谱
        PageResult<RecipeDO> pageResult = recipeMapper.selectPage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public Long createRecipeComment(Long userId, AppRecipeCommentSaveReqVO createReqVO) {
        UserCommentDO userCommentDO = BeanUtils.toBean(createReqVO, UserCommentDO.class);
        // 用户信息
        MemberUserRespDTO user = memberUserApi.getUser(userId);
        userCommentDO.setUserId(userId); // 用户ID
        userCommentDO.setUserNickname(user.getNickname()); // 用户昵称
        userCommentDO.setUserAvatar(user.getAvatar()); // 用户头像
        // 内容类型：菜谱
        userCommentDO.setContentType(ContentTypesEnum.RECIPE.getType());
        userCommentDO.setContentId(createReqVO.getRecipeId()); // 内容编码
        // 是否内容作者
        userCommentDO.setCommentAuthor(recipeMapper.exists(new LambdaQueryWrapperX<RecipeDO>()
                .eq(RecipeDO::getUserId, userId) // 评论人的
                .eq(RecipeDO::getRecipeType, RecipeTypesEnum.USER.getType()) // 菜谱类型为用户的
                .eq(RecipeDO::getId, createReqVO.getRecipeId()) // 该菜谱
        ));
        // 内容作者
        if (userCommentDO.getCommentAuthor()) {
            // 如果是内容作者，直接赋值评论人的id到内容作者上
            userCommentDO.setContentUserId(userCommentDO.getUserId());
        } else {
            // 如果是不是内容作者，赋值内容的作者的id到内容作者上，如果不为用户菜谱，则不赋值
            RecipeDO contentRecipe = recipeMapper.selectOne(new LambdaQueryWrapperX<RecipeDO>()
                    .eq(RecipeDO::getId, createReqVO.getRecipeId())
                    .eq(RecipeDO::getRecipeType, RecipeTypesEnum.USER.getType()));
            if (Objects.nonNull(contentRecipe)) {
                userCommentDO.setContentId(contentRecipe.getId());
            }
        }
        // 插入内容
        userCommentMapper.insert(userCommentDO);
        // 返回ID
        return userCommentDO.getId();
    }

    @Override
    public PageResult<AppRecipeRespVO> getFavorUsersRecipePage(Long userId, AppRecipePageReqVO pageReqVO) {
        // 关注用户的菜谱
        pageReqVO.setUserFavor(Boolean.TRUE);
        // 查询基础信息
        PageResult<RecipeDO> pageResult = recipeMapper.getUserViewableRecipePage(userId, pageReqVO);
        List<RecipeDO> recipes = pageResult.getList(); // 菜谱信息
        if (CollUtil.isEmpty(recipes)) {
            // 为空直接返回
            return BeanUtils.toBean(pageResult, AppRecipeRespVO.class);
        }
        // 查询菜谱食材信息
        List<RecipeFoodDO> recipeFoods = getRecipeFoodsByRecipeIds(convertSet(recipes, RecipeDO::getId));
        // 装载信息
        return RecipeConvert.INSTANCE.convertPage(pageResult,recipeFoods);
    }

    @Override
    public PageResult<AppRecipeRespVO> getRecipePageByUser(AppRecipePageReqVO pageReqVO) {
        pageReqVO.setRecipeType(RecipeTypesEnum.USER.getType()); // 用户类型
        pageReqVO.setStatus(RecipeStatusEnum.PUBLIC.getType()); // 公开的
        return BeanUtils.toBean(recipeMapper.selectPage(pageReqVO.getUserId(), pageReqVO),  AppRecipeRespVO.class);
    }

    /**
     * 通过菜谱ID集合获取所有菜谱的食材信息
     * @param recipeIds 菜谱ID
     * @return 菜谱食材集合
     */
    private List<RecipeFoodDO> getRecipeFoodsByRecipeIds(Set<Long> recipeIds) {
        return recipeFoodMapper.selectList(new LambdaQueryWrapperX<RecipeFoodDO>()
                // 查询所有分页菜谱的食材信息
                .in(RecipeFoodDO::getRecipeId, recipeIds));
    }

    private void validateRecipeExists(Long id) {
        if (recipeMapper.selectById(id) == null) {
            throw exception(RECIPE_NOT_EXISTS);
        }
    }
}
