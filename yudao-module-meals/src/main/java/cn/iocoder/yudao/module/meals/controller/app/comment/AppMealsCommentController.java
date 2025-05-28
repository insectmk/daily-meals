package cn.iocoder.yudao.module.meals.controller.app.comment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.comment.vo.AppMealsCommentPageReqVO;
import cn.iocoder.yudao.module.meals.controller.app.comment.vo.AppMealsCommentRespVO;
import cn.iocoder.yudao.module.meals.controller.app.comment.vo.AppMealsCommentSaveReqVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.comment.MealsCommentDO;
import cn.iocoder.yudao.module.meals.service.comment.AppMealsCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 评论")
@RestController
@RequestMapping("/meals/comment")
@Validated
public class AppMealsCommentController {

    @Resource
    private AppMealsCommentService appMealsCommentService;

    @PostMapping("/create")
    @Operation(summary = "创建评论")
    public CommonResult<Long> createComment(@Valid @RequestBody AppMealsCommentSaveReqVO createReqVO) {
        return success(appMealsCommentService.createComment(createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteComment(@RequestParam("id") Long id) {
        appMealsCommentService.deleteComment(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评论")
    public CommonResult<Boolean> deleteCommentList(@RequestParam("ids") List<Long> ids) {
        appMealsCommentService.deleteCommentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppMealsCommentRespVO> getComment(@RequestParam("id") Long id) {
        MealsCommentDO comment = appMealsCommentService.getComment(id);
        return success(BeanUtils.toBean(comment, AppMealsCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    public CommonResult<PageResult<AppMealsCommentRespVO>> getCommentPage(@Valid AppMealsCommentPageReqVO pageReqVO) {
        PageResult<MealsCommentDO> pageResult = appMealsCommentService.getCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppMealsCommentRespVO.class));
    }
}
