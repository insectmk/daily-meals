package cn.iocoder.yudao.module.meals.service.userfavor;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.dal.mysql.userfavor.UserFavorMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
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

    @Override
    public Long createUserFavor(AppUserFavorSaveReqVO createReqVO) {
        // 插入
        UserFavorDO userFavor = BeanUtils.toBean(createReqVO, UserFavorDO.class);
        userFavorMapper.insert(userFavor);
        // 返回
        return userFavor.getId();
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

}
