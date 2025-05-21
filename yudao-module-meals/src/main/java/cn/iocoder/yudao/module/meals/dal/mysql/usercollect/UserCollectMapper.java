package cn.iocoder.yudao.module.meals.dal.mysql.usercollect;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.*;

/**
 * 用户收藏夹 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface UserCollectMapper extends BaseMapperX<UserCollectDO> {

    default PageResult<UserCollectDO> selectPage(AppUserCollectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserCollectDO>()
                .eqIfPresent(UserCollectDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserCollectDO::getContentType, reqVO.getContentType())
                .likeIfPresent(UserCollectDO::getCollectName, reqVO.getCollectName())
                .eqIfPresent(UserCollectDO::getPicUrl, reqVO.getPicUrl())
                .eqIfPresent(UserCollectDO::getCollectDesc, reqVO.getCollectDesc())
                .eqIfPresent(UserCollectDO::getCollectStatus, reqVO.getCollectStatus())
                .betweenIfPresent(UserCollectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserCollectDO::getId));
    }

}