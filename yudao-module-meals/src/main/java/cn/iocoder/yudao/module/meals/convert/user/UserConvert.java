package cn.iocoder.yudao.module.meals.convert.user;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.recipe.vo.RecipeSimpleRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeFoodRespVO;
import cn.iocoder.yudao.module.meals.controller.app.recipe.vo.AppRecipeRespVO;
import cn.iocoder.yudao.module.meals.controller.app.user.vo.AppUserInfoRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMultiMap;

/**
 * @Title: ReceipeConvert
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.convert.recipe
 * @Date 2025/6/6 10:05
 * @description: 用户对象转换
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * 将用户信息，转为是否关注的用户信息
     * @param userDOPage DO分页数据
     * @param favorContentIds 关注的ID
     * @return
     */
    default PageResult<AppUserInfoRespVO> convertFavorPage(PageResult<MemberUserDO> userDOPage, Set<Long> favorContentIds) {
        PageResult<AppUserInfoRespVO> pageResult = BeanUtils.toBean(userDOPage, AppUserInfoRespVO.class);
        pageResult.getList().forEach(userInfo -> {
            // 如果关注列表里存在用户ID则标识为喜欢
            userInfo.setFavor(favorContentIds.contains(userInfo.getId()));
        });
        return pageResult;
    }
}
