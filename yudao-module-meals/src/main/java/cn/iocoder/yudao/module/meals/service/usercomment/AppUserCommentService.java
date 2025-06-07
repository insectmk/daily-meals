package cn.iocoder.yudao.module.meals.service.usercomment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercomment.UserCommentDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 评论 Service 接口
 *
 * @author InsectMk
 */
public interface AppUserCommentService {

    /**
     * 更新评论
     *
     * @param updateReqVO 更新信息
     */
    void updateComment(@Valid AppUserCommentSaveReqVO updateReqVO);

    /**
     * 删除评论
     *
     * @param id 编号
     */
    void deleteComment(Long id);

    /**
    * 批量删除评论
    *
    * @param ids 编号
    */
    void deleteCommentListByIds(List<Long> ids);

    /**
     * 获得评论
     *
     * @param id 编号
     * @return 评论
     */
    UserCommentDO getComment(Long id);

    /**
     * 获得评论分页
     *
     * @param pageReqVO 分页查询
     * @return 评论分页
     */
    PageResult<UserCommentDO> getCommentPage(AppUserCommentPageReqVO pageReqVO);

}
