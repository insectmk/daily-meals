package cn.iocoder.yudao.module.meals.controller.admin.comment;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.meals.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.comment.MealsCommentDO;
import cn.iocoder.yudao.module.meals.service.comment.MealsCommentService;

@Tag(name = "管理后台 - 评论")
@RestController
@RequestMapping("/meals/comment")
@Validated
public class MealsCommentController {

    @Resource
    private MealsCommentService commentService;

    @PostMapping("/create")
    @Operation(summary = "创建评论")
    @PreAuthorize("@ss.hasPermission('meals:comment:create')")
    public CommonResult<Long> createComment(@Valid @RequestBody MealsCommentSaveReqVO createReqVO) {
        return success(commentService.createComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论")
    @PreAuthorize("@ss.hasPermission('meals:comment:update')")
    public CommonResult<Boolean> updateComment(@Valid @RequestBody MealsCommentSaveReqVO updateReqVO) {
        commentService.updateComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('meals:comment:delete')")
    public CommonResult<Boolean> deleteComment(@RequestParam("id") Long id) {
        commentService.deleteComment(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评论")
                @PreAuthorize("@ss.hasPermission('meals:comment:delete')")
    public CommonResult<Boolean> deleteCommentList(@RequestParam("ids") List<Long> ids) {
        commentService.deleteCommentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('meals:comment:query')")
    public CommonResult<MealsCommentRespVO> getComment(@RequestParam("id") Long id) {
        MealsCommentDO comment = commentService.getComment(id);
        return success(BeanUtils.toBean(comment, MealsCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    @PreAuthorize("@ss.hasPermission('meals:comment:query')")
    public CommonResult<PageResult<MealsCommentRespVO>> getCommentPage(@Valid MealsCommentPageReqVO pageReqVO) {
        PageResult<MealsCommentDO> pageResult = commentService.getCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MealsCommentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论 Excel")
    @PreAuthorize("@ss.hasPermission('meals:comment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCommentExcel(@Valid MealsCommentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MealsCommentDO> list = commentService.getCommentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评论.xls", "数据", MealsCommentRespVO.class,
                        BeanUtils.toBean(list, MealsCommentRespVO.class));
    }

}