package cn.iocoder.yudao.module.meals.dal.mysql.usercomment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentPageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercomment.UserCommentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface UserCommentMapper extends BaseMapperX<UserCommentDO> {

    default PageResult<UserCommentDO> selectPage(AppUserCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserCommentDO>()
                .eqIfPresent(UserCommentDO::getUserId, reqVO.getUserId())
                .likeIfPresent(UserCommentDO::getUserNickname, reqVO.getUserNickname())
                .eqIfPresent(UserCommentDO::getUserAvatar, reqVO.getUserAvatar())
                .eqIfPresent(UserCommentDO::getContentId, reqVO.getContentId())
                .eqIfPresent(UserCommentDO::getContentType, reqVO.getContentType())
                .eqIfPresent(UserCommentDO::getContentUserId, reqVO.getContentUserId()) // 内容所属人
                .eqIfPresent(UserCommentDO::getCommentContent, reqVO.getCommentContent())
                .eqIfPresent(UserCommentDO::getCommentAuthor, reqVO.getCommentAuthor())
                .eqIfPresent(UserCommentDO::getUserRead, reqVO.getUserRead()) // 是否已读
                .eqIfPresent(UserCommentDO::getPicUrls, reqVO.getPicUrls())
                .eqIfPresent(UserCommentDO::getReplyUserId, reqVO.getReplyUserId())
                .likeIfPresent(UserCommentDO::getReplyUserNickname, reqVO.getReplyUserNickname())
                .eqIfPresent(UserCommentDO::getReplyContent, reqVO.getReplyContent())
                .betweenIfPresent(UserCommentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserCommentDO::getId));
    }

}
