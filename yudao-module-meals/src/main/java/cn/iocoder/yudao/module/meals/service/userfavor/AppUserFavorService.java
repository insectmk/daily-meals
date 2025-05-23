package cn.iocoder.yudao.module.meals.service.userfavor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorDelReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 用户收藏 Service 接口
 *
 * @author InsectMk
 */
public interface AppUserFavorService {

    /**
     * 创建用户收藏
     *
     * @param userId 用户ID
     * @param createReqVO 创建信息
     */
    void createUserFavor(Long userId, @Valid AppUserFavorSaveReqVO createReqVO);

    /**
     * 更新用户收藏
     *
     * @param updateReqVO 更新信息
     */
    void updateUserFavor(@Valid AppUserFavorSaveReqVO updateReqVO);

    /**
     * 删除用户收藏
     *
     * @param id 编号
     */
    void deleteUserFavor(Long id);

    /**
    * 批量删除用户收藏
    *
    * @param ids 编号
    */
    void deleteUserFavorListByIds(List<Long> ids);

    /**
     * 获得用户收藏
     *
     * @param id 编号
     * @return 用户收藏
     */
    UserFavorDO getUserFavor(Long id);

    /**
     * 获得用户收藏分页
     *
     * @param pageReqVO 分页查询
     * @return 用户收藏分页
     */
    PageResult<UserFavorDO> getUserFavorPage(AppUserFavorPageReqVO pageReqVO);;

    /**
     * 取消收藏
     * @param userId 用户ID
     * @param delReqVO 内容
     */
    void cancelUserFavor(Long userId, @Valid AppUserFavorDelReqVO delReqVO);

    /**
     * 添加到默认收藏夹
     * @param userId 用户ID
     * @param createReqVO 收藏信息
     * @return 加入的收藏夹信息
     */
    UserCollectDO addToDefaultCollect(Long userId, @Valid AppUserFavorSaveReqVO createReqVO);
}
