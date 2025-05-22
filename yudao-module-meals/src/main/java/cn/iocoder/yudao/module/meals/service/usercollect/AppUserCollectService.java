package cn.iocoder.yudao.module.meals.service.usercollect;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 用户收藏夹 Service 接口
 *
 * @author InsectMk
 */
public interface AppUserCollectService {

    /**
     * 创建用户收藏夹
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserCollect(@Valid AppUserCollectSaveReqVO createReqVO);

    /**
     * 更新用户收藏夹
     *
     * @param updateReqVO 更新信息
     */
    void updateUserCollect(@Valid AppUserCollectSaveReqVO updateReqVO);

    /**
     * 删除用户收藏夹
     *
     * @param id 编号
     */
    void deleteUserCollect(Long id);

    /**
    * 批量删除用户收藏夹
    *
    * @param ids 编号
    */
    void deleteUserCollectListByIds(List<Long> ids);

    /**
     * 获得用户收藏夹
     *
     * @param id 编号
     * @return 用户收藏夹
     */
    UserCollectDO getUserCollect(Long id);

    /**
     * 获得用户收藏夹分页
     *
     * @param pageReqVO 分页查询
     * @return 用户收藏夹分页
     */
    PageResult<UserCollectDO> getUserCollectPage(AppUserCollectPageReqVO pageReqVO);

    // ==================== 子表（用户收藏） ====================

    /**
     * 获得用户收藏列表
     *
     * @param collectId 收藏夹ID
     * @return 用户收藏列表
     */
    List<UserFavorDO> getUserFavorListByCollectId(Long collectId);

    /**
     * 创建或更新用户收藏夹
     * @param userId 用户ID
     * @param createReqVO 收藏夹信息
     * @return 收藏夹ID
     */
    Long createOrUpdateUserCollect(Long userId, @Valid AppUserCollectSaveReqVO createReqVO);
}
