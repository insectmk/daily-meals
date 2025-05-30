package cn.iocoder.yudao.module.meals.dal.mysql.usercollect;

import java.util.*;
import java.util.function.Predicate;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.*;

/**
 * 用户收藏夹 Mapper
 *
 * @author InsectMk
 */
@Mapper
public interface UserCollectMapper extends BaseMapperX<UserCollectDO> {
    /**
     * 获取用户收藏夹列表，根据某个内容，带是否在收藏夹中的判断
     * @param userId 用户ID
     * @param reqVO 请求参数
     * @return 列表内容
     */
    default List<AppUserCollectSimpleRespVO> selectUserListByContent(Long userId, AppUserCollectSimpleListReqVO reqVO) {
        List<Map<String, Object>> result = this.selectMaps(new QueryWrapper<UserCollectDO>()
                .select(String.format("id, collect_name, collect_desc, collect_status, exists(select 1 from meals_user_favor uf where uf.collect_id = meals_user_collect.id and uf.user_id = %d and uf.content_type = %d and uf.content_id = %d and uf.deleted = 0) as selected", userId, reqVO.getContentType(), reqVO.getContentId()))
                .eq("user_id", userId) // 该用户的
                .eq("content_type", reqVO.getContentType()) // 对应内容类型
                .orderByDesc("id"));
        return BeanUtil.copyToList(result, AppUserCollectSimpleRespVO.class);
    }

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

    /**
     * 获取用户自己的分页信息
     * @param userId 用户ID
     * @param reqVO 分页参数
     * @return 分页信息
     */
    default PageResult<UserCollectDO> selectSelfPage(Long userId, AppUserCollectPageReqVO reqVO){
        return selectPage(reqVO, new LambdaQueryWrapperX<UserCollectDO>()
                .eqIfPresent(UserCollectDO::getUserId, userId)
                .eqIfPresent(UserCollectDO::getContentType, reqVO.getContentType())
                .likeIfPresent(UserCollectDO::getCollectName, reqVO.getCollectName())
                .eqIfPresent(UserCollectDO::getPicUrl, reqVO.getPicUrl())
                .eqIfPresent(UserCollectDO::getCollectDesc, reqVO.getCollectDesc())
                .eqIfPresent(UserCollectDO::getCollectStatus, reqVO.getCollectStatus())
                .betweenIfPresent(UserCollectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserCollectDO::getId));
    }
}
