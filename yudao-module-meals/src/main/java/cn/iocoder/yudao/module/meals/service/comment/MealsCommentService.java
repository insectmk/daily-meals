package cn.iocoder.yudao.module.meals.service.comment;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.meals.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.comment.MealsCommentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 评论 Service 接口
 *
 * @author InsectMk
 */
public interface MealsCommentService {

    /**
     * 创建评论
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComment(@Valid MealsCommentSaveReqVO createReqVO);

    /**
     * 更新评论
     *
     * @param updateReqVO 更新信息
     */
    void updateComment(@Valid MealsCommentSaveReqVO updateReqVO);

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
    MealsCommentDO getComment(Long id);

    /**
     * 获得评论分页
     *
     * @param pageReqVO 分页查询
     * @return 评论分页
     */
    PageResult<MealsCommentDO> getCommentPage(MealsCommentPageReqVO pageReqVO);

}