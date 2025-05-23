package cn.iocoder.yudao.module.meals.convert.usercollect;

import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectSimpleRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Title: UserCollectConvert
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.convert.usercollect
 * @Date 2025/5/23 10:05
 * @description: 用户收藏夹对象转换
 */
@Mapper
public interface UserCollectConvert {
    UserCollectConvert INSTANCE = Mappers.getMapper(UserCollectConvert.class);

    /**
     * 将收藏夹转为收藏夹精简信息列表
     * @param list
     * @return
     */
    List<AppUserCollectSimpleRespVO> convertSimpleList(List<UserCollectDO> list);
}
