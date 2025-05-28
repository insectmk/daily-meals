package cn.iocoder.yudao.module.meals.service.comment;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.comment.vo.MealsCommentPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.comment.vo.AppMealsCommentPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.comment.vo.AppMealsCommentSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.comment.MealsCommentDO;
import cn.iocoder.yudao.module.meals.dal.mysql.comment.MealsCommentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.meals.enums.ErrorCodeConstants.COMMENT_NOT_EXISTS;

/**
 * 评论 Service 实现类
 *
 * @author InsectMk
 */
@Service
@Validated
public class AppMealsCommentServiceImpl implements AppMealsCommentService {

    @Resource
    private MealsCommentMapper commentMapper;

    @Override
    public Long createComment(AppMealsCommentSaveReqVO createReqVO) {
        // 插入
        MealsCommentDO comment = BeanUtils.toBean(createReqVO, MealsCommentDO.class);
        commentMapper.insert(comment);
        // 返回
        return comment.getId();
    }

    @Override
    public void updateComment(AppMealsCommentSaveReqVO updateReqVO) {
        // 校验存在
        validateCommentExists(updateReqVO.getId());
        // 更新
        MealsCommentDO updateObj = BeanUtils.toBean(updateReqVO, MealsCommentDO.class);
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
        List<MealsCommentDO> list = commentMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(COMMENT_NOT_EXISTS);
        }
    }

    private void validateCommentExists(Long id) {
        if (commentMapper.selectById(id) == null) {
            throw exception(COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public MealsCommentDO getComment(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    public PageResult<MealsCommentDO> getCommentPage(AppMealsCommentPageReqVO pageReqVO) {
        return commentMapper.selectPage(BeanUtils.toBean(pageReqVO, MealsCommentPageReqVO.class));
    }

}
