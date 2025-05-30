package cn.iocoder.yudao.module.meals.controller.app.usercomment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentRespVO;
import cn.iocoder.yudao.module.meals.controller.app.usercomment.vo.AppUserCommentSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.usercomment.UserCommentDO;
import cn.iocoder.yudao.module.meals.service.usercomment.AppUserCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 评论")
@RestController
@RequestMapping("/meals/user-comment")
@Validated
public class AppUserCommentController {

    @Resource
    private AppUserCommentService appUserCommentService;

    @PostMapping("/create")
    @Operation(summary = "创建评论")
    public CommonResult<Long> createComment(@Valid @RequestBody AppUserCommentSaveReqVO createReqVO) {
        return success(appUserCommentService.createComment(getLoginUserId(),createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteComment(@RequestParam("id") Long id) {
        appUserCommentService.deleteComment(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评论")
    public CommonResult<Boolean> deleteCommentList(@RequestParam("ids") List<Long> ids) {
        appUserCommentService.deleteCommentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppUserCommentRespVO> getComment(@RequestParam("id") Long id) {
        UserCommentDO comment = appUserCommentService.getComment(id);
        return success(BeanUtils.toBean(comment, AppUserCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    public CommonResult<PageResult<AppUserCommentRespVO>> getCommentPage(@Valid AppUserCommentPageReqVO pageReqVO) {
        PageResult<UserCommentDO> pageResult = appUserCommentService.getCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppUserCommentRespVO.class));
    }
}
