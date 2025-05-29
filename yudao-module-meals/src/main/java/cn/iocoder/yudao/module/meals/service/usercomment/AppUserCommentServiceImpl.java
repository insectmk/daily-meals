package cn.iocoder.yudao.module.meals.service.usercomment;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercomment.UserCommentDO;
import cn.iocoder.yudao.module.meals.dal.mysql.usercomment.UserCommentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.USER_COMMENT_NOT_EXISTS;

/**
 * 评论 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppUserCommentServiceImpl implements AppUserCommentService {

    @Resource
    private UserCommentMapper commentMapper;

    @Override
    public Long createComment(AppUserCommentSaveReqVO createReqVO) {
        // 插入
        UserCommentDO comment = BeanUtils.toBean(createReqVO, UserCommentDO.class);
        commentMapper.insert(comment);
        // 返回
        return comment.getId();
    }

    @Override
    public void updateComment(AppUserCommentSaveReqVO updateReqVO) {
        // 校验存在
        validateCommentExists(updateReqVO.getId());
        // 更新
        UserCommentDO updateObj = BeanUtils.toBean(updateReqVO, UserCommentDO.class);
        commentMapper.updateById(updateObj);
    }

    @Override
    public void deleteComment(Long id) {
        // 校验存在
        validateCommentExists(id);
        // 删除
        commentMapper.deleteById(id);
    }

    @Override
        public void deleteCommentListByIds(List<Long> ids) {
        // 校验存在
        validateCommentExists(ids);
        // 删除
        commentMapper.deleteByIds(ids);
        }

    private void validateCommentExists(List<Long> ids) {
        List<UserCommentDO> list = commentMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(USER_COMMENT_NOT_EXISTS);
        }
    }

    private void validateCommentExists(Long id) {
        if (commentMapper.selectById(id) == null) {
            throw exception(USER_COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public UserCommentDO getComment(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    public PageResult<UserCommentDO> getCommentPage(AppUserCommentPageReqVO pageReqVO) {
        return commentMapper.selectPage(pageReqVO);
    }

}
