package cn.iocoder.yudao.module.meals.service.usercollect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeFoodSaveReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.dal.mysql.usercollect.UserCollectMapper;
import cn.iocoder.yudao.module.meals.dal.mysql.userfavor.UserFavorMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.USER_COLLECT_NOT_EXISTS;

/**
 * 用户收藏夹 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppUserCollectServiceImpl implements AppUserCollectService {

    @Resource
    private UserCollectMapper userCollectMapper;
    @Resource
    private UserFavorMapper userFavorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUserCollect(AppUserCollectSaveReqVO createReqVO) {
        // 插入
        UserCollectDO userCollect = BeanUtils.toBean(createReqVO, UserCollectDO.class);
        userCollectMapper.insert(userCollect);

        // 插入子表
        createUserFavorList(userCollect.getId(), createReqVO.getUserFavors());
        // 返回
        return userCollect.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserCollect(AppUserCollectSaveReqVO updateReqVO) {
        // 校验存在
        validateUserCollectExists(updateReqVO.getId());
        // 更新
        UserCollectDO updateObj = BeanUtils.toBean(updateReqVO, UserCollectDO.class);
        userCollectMapper.updateById(updateObj);

        // 更新子表
        updateUserFavorList(updateReqVO.getId(), updateReqVO.getUserFavors());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserCollect(Long id) {
        // 校验存在
        validateUserCollectExists(id);
        // 删除
        userCollectMapper.deleteById(id);

        // 删除子表
        deleteUserFavorByCollectId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteUserCollectListByIds(List<Long> ids) {
        // 校验存在
        validateUserCollectExists(ids);
        // 删除
        userCollectMapper.deleteByIds(ids);

    // 删除子表
            deleteUserFavorByCollectIds(ids);
    }

    private void validateUserCollectExists(List<Long> ids) {
        List<UserCollectDO> list = userCollectMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(USER_COLLECT_NOT_EXISTS);
        }
    }

    private void validateUserCollectExists(Long id) {
        if (userCollectMapper.selectById(id) == null) {
            throw exception(USER_COLLECT_NOT_EXISTS);
        }
    }

    @Override
    public UserCollectDO getUserCollect(Long id) {
        return userCollectMapper.selectById(id);
    }

    @Override
    public PageResult<UserCollectDO> getUserCollectPage(AppUserCollectPageReqVO pageReqVO) {
        return userCollectMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrUpdateUserCollect(Long userId, AppUserCollectSaveReqVO createReqVO) {
        Long collectId = createReqVO.getId();
        // 根据id判断新增还是修改
        if (Objects.isNull(collectId)) {
            // 为空：新增
            createReqVO.setUserId(userId); // 设置用户ID
            return this.createUserCollect(createReqVO);
        }
        // 更新
        // 1. 更新基础数据
        validateUserCollectExists(collectId); // 校验存在
        this.updateUserCollect(createReqVO);
        // 返回
        return collectId;
    }

    @Override
    public PageResult<UserCollectDO> getSelfUserCollectPage(Long userId, AppUserCollectPageReqVO pageReqVO) {
        return userCollectMapper.selectSelfPage(userId, pageReqVO);
    }

    // ==================== 子表（用户收藏） ====================

    @Override
    public List<UserFavorDO> getUserFavorListByCollectId(Long collectId) {
        return userFavorMapper.selectListByCollectId(collectId);
    }

    private void createUserFavorList(Long collectId, List<UserFavorDO> list) {
        list.forEach(o -> o.setCollectId(collectId).clean());
        userFavorMapper.insertBatch(list);
    }

    private void updateUserFavorList(Long collectId, List<UserFavorDO> list) {
	    list.forEach(o -> o.setCollectId(collectId).clean());
	    List<UserFavorDO> oldList = userFavorMapper.selectListByCollectId(collectId);
	    List<List<UserFavorDO>> diffList = diffList(oldList, list, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getId(), newVal.getId());
            if (same) {
                newVal.setId(oldVal.getId()).clean(); // 解决更新情况下：updateTime 不更新
            }
            return same;
	    });

	    // 第二步，批量添加、修改、删除
	    if (CollUtil.isNotEmpty(diffList.get(0))) {
	        userFavorMapper.insertBatch(diffList.get(0));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(1))) {
	        userFavorMapper.updateBatch(diffList.get(1));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(2))) {
	        userFavorMapper.deleteByIds(convertList(diffList.get(2), UserFavorDO::getId));
	    }
    }

    private void deleteUserFavorByCollectId(Long collectId) {
        userFavorMapper.deleteByCollectId(collectId);
    }

	private void deleteUserFavorByCollectIds(List<Long> collectIds) {
        userFavorMapper.deleteByCollectIds(collectIds);
	}

}
