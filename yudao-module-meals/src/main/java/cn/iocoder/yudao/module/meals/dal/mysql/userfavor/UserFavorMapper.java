package cn.iocoder.yudao.module.meals.dal.mysql.userfavor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorPageReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 用户收藏 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface UserFavorMapper extends BaseMapperX<UserFavorDO> {
    /**
     * 获取内容是否被收藏
     * @param contentIds 内容ID集合
     * @param contentType 内容类型
     * @param userId 用户编号
     * @return contentId favor;内容ID 是否喜欢/收藏
     */
    default Set<Long> getFavorContentIds(Set<Long> contentIds, Integer contentType, Long userId) {
        // SQL 查询
        List<UserFavorDO> userFavorDOS = this.selectList(new LambdaQueryWrapper<UserFavorDO>()
                .select(true, UserFavorDO::getContentId)
                .eq(UserFavorDO::getContentType, contentType) // 对应内容类型
                .eq(UserFavorDO::getUserId, userId) // 对应用户
                .in(UserFavorDO::getContentId, contentIds) // 对应内容
        );
        return convertSet(userFavorDOS, UserFavorDO::getContentId);
    }

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
