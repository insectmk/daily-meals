package cn.iocoder.yudao.module.meals.service.userfavor;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorDelReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.dal.mysql.usercollect.UserCollectMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.userfavor.UserFavorMapper;
import cn.iocoder.yudao.module.meals.enums.RecipeStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.USER_FAVOR_ALREADY_CANCEL;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.USER_FAVOR_NOT_EXISTS;

/**
 * 用户收藏 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppUserFavorServiceImpl implements AppUserFavorService {

    @Resource
    private UserFavorMapper userFavorMapper;
    @Resource
    private UserCollectMapper userCollectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserFavor(Long userId, AppUserFavorSaveReqVO createReqVO) {
        // 删除所有的收藏
        userFavorMapper.delete(new LambdaQueryWrapperX<UserFavorDO>()
                .eq(UserFavorDO::getUserId, userId) // 该用户
                .eq(UserFavorDO::getContentType, createReqVO.getContentType()) // 指定内容类型
                .eq(UserFavorDO::getContentId, createReqVO.getContentId()) // 指定内容
        );
        // 判断是否传入收藏夹id
        List<Long> collectIds = createReqVO.getCollectIds();
        UserFavorDO userFavor = BeanUtils.toBean(createReqVO, UserFavorDO.class);
        if (CollUtil.isEmpty(collectIds)) {
            addToDefaultCollect(userId, createReqVO); // 加入到默认收藏夹中
            return;
        }
        // 插入
        List<UserFavorDO> userFavors = new ArrayList<>(collectIds.size());
        for (Long collectId : collectIds) {
            UserFavorDO userFavorInsertDO = new UserFavorDO();
            BeanUtils.copyProperties(userFavor, userFavorInsertDO); // 复制到新对象
            userFavorInsertDO.setCollectId(collectId); // 设置收藏夹
            userFavorInsertDO.setUserId(userId); // 设置用户ID
            userFavors.add(userFavorInsertDO);
        }
        userFavorMapper.insertBatch(userFavors); // 批量插入

    }

    @Override
    public void updateUserFavor(AppUserFavorSaveReqVO updateReqVO) {
        // 校验存在
        validateUserFavorExists(updateReqVO.getId());
        // 更新
        UserFavorDO updateObj = BeanUtils.toBean(updateReqVO, UserFavorDO.class);
        userFavorMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserFavor(Long id) {
        // 校验存在
        validateUserFavorExists(id);
        // 删除
        userFavorMapper.deleteById(id);
    }

    @Override
        public void deleteUserFavorListByIds(List<Long> ids) {
        // 校验存在
        validateUserFavorExists(ids);
        // 删除
        userFavorMapper.deleteByIds(ids);
        }

    private void validateUserFavorExists(List<Long> ids) {
        List<UserFavorDO> list = userFavorMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(USER_FAVOR_NOT_EXISTS);
        }
    }

    private void validateUserFavorExists(Long id) {
        if (userFavorMapper.selectById(id) == null) {
            throw exception(USER_FAVOR_NOT_EXISTS);
        }
    }

    @Override
    public UserFavorDO getUserFavor(Long id) {
        return userFavorMapper.selectById(id);
    }

    @Override
    public PageResult<UserFavorDO> getUserFavorPage(AppUserFavorPageReqVO pageReqVO) {
        return userFavorMapper.selectPage(pageReqVO);
    }

    @Override
    public void cancelUserFavor(Long userId, AppUserFavorDelReqVO delReqVO) {
        LambdaQueryWrapperX<UserFavorDO> delQueryWrapper = new LambdaQueryWrapperX<UserFavorDO>()
                .eq(UserFavorDO::getUserId, userId)
                .eq(UserFavorDO::getContentType, delReqVO.getContentType())
                .eq(UserFavorDO::getContentId, delReqVO.getContentId());
        // 校验存在
        if (userFavorMapper.exists(delQueryWrapper)) {
            // 删除
            userFavorMapper.delete(delQueryWrapper);
        } else {
            throw exception(USER_FAVOR_ALREADY_CANCEL);
        }
    }

    @Override
    public UserCollectDO addToDefaultCollect(Long userId, AppUserFavorSaveReqVO createReqVO) {
        UserFavorDO userFavor = BeanUtils.toBean(createReqVO, UserFavorDO.class);
        // 加入到默认中
        UserCollectDO userCollectDO = userCollectMapper.selectFirstOne(
                UserCollectDO::getUserId, userId, // 该用户
                UserCollectDO::getContentType, userFavor.getContentType(), // 内容类型
                UserCollectDO::getDefaultFlag, true // 默认内容
        );
        if (Objects.isNull(userCollectDO)) {
            // 创建一个默认菜谱
            userCollectDO = new UserCollectDO();
            userCollectDO.setUserId(userId); // 用户
            userCollectDO.setContentType(userFavor.getContentType()); // 内容类型
            userCollectDO.setCollectName("默认收藏夹"); // 收藏夹名称
            userCollectDO.setCollectDesc("默认收藏夹"); // 简介
            userCollectDO.setCollectStatus(RecipeStatusEnum.PRIVATE.getType()); // 状态 - 私有
            userCollectDO.setDefaultFlag(true); // 是否为默认收藏夹
            userCollectMapper.insert(userCollectDO);
        }
        // 设置收藏夹
        userFavor.setCollectId(userCollectDO.getId());
        // 设置用户
        userFavor.setUserId(userId);
        userFavorMapper.insert(userFavor);
        return userCollectDO;
    }
}
