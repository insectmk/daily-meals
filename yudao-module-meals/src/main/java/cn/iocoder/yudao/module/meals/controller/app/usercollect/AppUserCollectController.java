package cn.iocoder.yudao.module.meals.controller.app.usercollect;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectRespVO;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectSaveReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercollect.vo.AppUserCollectSimpleRespVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.service.usercollect.AppUserCollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 用户收藏夹")
@RestController
@RequestMapping("/meals/user-collect")
@Validated
public class AppUserCollectController {

    @Resource
    private AppUserCollectService appUserCollectService;

    @GetMapping("/list-self-all-simple")
    @Operation(summary = "展示自己所有的收藏夹精简信息")
    public CommonResult<List<AppUserCollectSimpleRespVO>> getSelfUserCollectAllSimpleList(@RequestParam("contentType") Integer contentType) {
        return success(appUserCollectService.getSelfUserCollectAllSimpleList(getLoginUserId(), contentType));
    }

    @GetMapping("/page-self")
    @Operation(summary = "获得用户自己的收藏夹分页")
    public CommonResult<PageResult<AppUserCollectRespVO>> getSelfUserCollectPage(@Valid AppUserCollectPageReqVO pageReqVO) {
        PageResult<UserCollectDO> pageResult = appUserCollectService.getSelfUserCollectPage(getLoginUserId(),pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppUserCollectRespVO.class));
    }

    @PostMapping("/create-or-update")
    @Operation(summary = "创建或更新用户收藏夹")
    public CommonResult<Long> createOrUpdateUserCollect(@Valid @RequestBody AppUserCollectSaveReqVO createReqVO) {
        return success(appUserCollectService.createOrUpdateUserCollect(getLoginUserId(), createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户收藏夹")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteUserCollect(@RequestParam("id") Long id) {
        appUserCollectService.deleteUserCollect(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除用户收藏夹")
    public CommonResult<Boolean> deleteUserCollectList(@RequestParam("ids") List<Long> ids) {
        appUserCollectService.deleteUserCollectListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户收藏夹")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppUserCollectRespVO> getUserCollect(@RequestParam("id") Long id) {
        UserCollectDO userCollect = appUserCollectService.getUserCollect(id);
        return success(BeanUtils.toBean(userCollect, AppUserCollectRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户收藏夹分页")
    public CommonResult<PageResult<AppUserCollectRespVO>> getUserCollectPage(@Valid AppUserCollectPageReqVO pageReqVO) {
        PageResult<UserCollectDO> pageResult = appUserCollectService.getUserCollectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppUserCollectRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户收藏夹 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserCollectExcel(@Valid AppUserCollectPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserCollectDO> list = appUserCollectService.getUserCollectPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户收藏夹.xls", "数据", AppUserCollectRespVO.class,
                        BeanUtils.toBean(list, AppUserCollectRespVO.class));
    }

    // ==================== 子表（用户收藏） ====================

    @GetMapping("/user-favor/list-by-collect-id")
    @Operation(summary = "获得用户收藏列表")
    @Parameter(name = "collectId", description = "收藏夹ID")
    public CommonResult<List<UserFavorDO>> getUserFavorListByCollectId(@RequestParam("collectId") Long collectId) {
        return success(appUserCollectService.getUserFavorListByCollectId(collectId));
    }

}
