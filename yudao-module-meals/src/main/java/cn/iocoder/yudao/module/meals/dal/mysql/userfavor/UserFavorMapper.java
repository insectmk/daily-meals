package cn.iocoder.yudao.module.meals.dal.mysql.userfavor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorPageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户收藏 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface UserFavorMapper extends BaseMapperX<UserFavorDO> {
    default PageResult<UserFavorDO> selectPage(AppUserFavorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserFavorDO>()
                .eqIfPresent(UserFavorDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserFavorDO::getContentId, reqVO.getContentId())
                .eqIfPresent(UserFavorDO::getContentType, reqVO.getContentType())
                .eqIfPresent(UserFavorDO::getCollectId, reqVO.getCollectId())
                .betweenIfPresent(UserFavorDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserFavorDO::getId));
    }

    default List<UserFavorDO> selectListByCollectId(Long collectId) {
        return selectList(UserFavorDO::getCollectId, collectId);
    }

    default int deleteByCollectId(Long collectId) {
        return delete(UserFavorDO::getCollectId, collectId);
    }

	default int deleteByCollectIds(List<Long> collectIds) {
	    return deleteBatch(UserFavorDO::getCollectId, collectIds);
	}
}
