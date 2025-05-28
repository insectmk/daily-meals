package cn.iocoder.yudao.module.meals.dal.mysql.comment;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.comment.MealsCommentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.admin.comment.vo.*;

/**
 * 评论 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface MealsCommentMapper extends BaseMapperX<MealsCommentDO> {

    default PageResult<MealsCommentDO> selectPage(MealsCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MealsCommentDO>()
                .eqIfPresent(MealsCommentDO::getUserId, reqVO.getUserId())
                .likeIfPresent(MealsCommentDO::getUserNickname, reqVO.getUserNickname())
                .eqIfPresent(MealsCommentDO::getUserAvatar, reqVO.getUserAvatar())
                .eqIfPresent(MealsCommentDO::getContentId, reqVO.getContentId())
                .eqIfPresent(MealsCommentDO::getContentType, reqVO.getContentType())
                .eqIfPresent(MealsCommentDO::getCommentContent, reqVO.getCommentContent())
                .eqIfPresent(MealsCommentDO::getCommentAuthor, reqVO.getCommentAuthor())
                .eqIfPresent(MealsCommentDO::getPicUrls, reqVO.getPicUrls())
                .eqIfPresent(MealsCommentDO::getReplyUserId, reqVO.getReplyUserId())
                .likeIfPresent(MealsCommentDO::getReplyUserNickname, reqVO.getReplyUserNickname())
                .eqIfPresent(MealsCommentDO::getReplyContent, reqVO.getReplyContent())
                .betweenIfPresent(MealsCommentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MealsCommentDO::getId));
    }

}