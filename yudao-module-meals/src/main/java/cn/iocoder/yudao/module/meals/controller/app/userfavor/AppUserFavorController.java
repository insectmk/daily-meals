package cn.iocoder.yudao.module.meals.controller.app.userfavor;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorDelReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorRespVO;
import cn.iocoder.yudao.module.meals.controller.app.userfavor.vo.AppUserFavorSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercollect.UserCollectDO;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;
import cn.iocoder.yudao.module.meals.service.userfavor.AppUserFavorService;
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

@Tag(name = "用户 APP - 用户收藏")
@RestController
@RequestMapping("/meals/user-favor")
@Validated
public class AppUserFavorController {

    @Resource
    private AppUserFavorService appUserFavorService;

    @PostMapping("/add-to-default")
    @Operation(summary = "添加到默认收藏夹")
    public CommonResult<UserCollectDO> addToDefaultCollect(@Valid @RequestBody AppUserFavorSaveReqVO createReqVO) {
        return success(appUserFavorService.addToDefaultCollect(getLoginUserId(), createReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "创建用户收藏")
    public CommonResult<Boolean> createUserFavor(@Valid @RequestBody AppUserFavorSaveReqVO createReqVO) {
        appUserFavorService.createUserFavor(getLoginUserId(), createReqVO);
        return success(true);
    }

    @DeleteMapping("/cancel")
    @Operation(summary = "取消用户收藏")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> cancelUserFavor(@Valid AppUserFavorDelReqVO delReqVO) {
        appUserFavorService.cancelUserFavor(getLoginUserId() ,delReqVO);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除用户收藏")
    public CommonResult<Boolean> deleteUserFavorList(@RequestParam("ids") List<Long> ids) {
        appUserFavorService.deleteUserFavorListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户收藏")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppUserFavorRespVO> getUserFavor(@RequestParam("id") Long id) {
        UserFavorDO userFavor = appUserFavorService.getUserFavor(id);
        return success(BeanUtils.toBean(userFavor, AppUserFavorRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户收藏分页")
    public CommonResult<PageResult<AppUserFavorRespVO>> getUserFavorPage(@Valid AppUserFavorPageReqVO pageReqVO) {
        PageResult<UserFavorDO> pageResult = appUserFavorService.getUserFavorPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppUserFavorRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户收藏 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserFavorExcel(@Valid AppUserFavorPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserFavorDO> list = appUserFavorService.getUserFavorPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户收藏.xls", "数据", AppUserFavorRespVO.class,
                        BeanUtils.toBean(list, AppUserFavorRespVO.class));
    }

}
