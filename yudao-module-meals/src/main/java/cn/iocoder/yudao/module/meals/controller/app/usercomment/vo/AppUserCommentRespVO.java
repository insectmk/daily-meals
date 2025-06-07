package cn.iocoder.yudao.module.meals.controller.app.usercomment.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 评论 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppUserCommentRespVO {

    @Schema(description = "评论编号，主键自增", requiredMode = Schema.RequiredMode.REQUIRED, example = "3522")
    @ExcelProperty("评论编号，主键自增")
    private Long id;

    @Schema(description = "评论人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10081")
    @ExcelProperty("评论人编号")
    private Long userId;

    @Schema(description = "评价人名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("评价人名称")
    private String userNickname;

    @Schema(description = "评价人头像", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("评价人头像")
    private String userAvatar;

    @Schema(description = "内容编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10501")
    @ExcelProperty("内容编号")
    private Long contentId;

    @Schema(description = "内容类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("内容类型")
    private Integer contentType;

    @Schema(description = "内容所属人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("内容所属人编号")
    private Long contentUserId;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("评论内容")
    private String commentContent;

    @Schema(description = "是否内容作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否内容作者")
    private Boolean commentAuthor;

    @Schema(description = "是否已读", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否内容作者")
    private Boolean userRead;

    @Schema(description = "评论图片地址数组")
    @ExcelProperty("评论图片地址数组")
    private String[] picUrls;

    @Schema(description = "回复的用户编号", example = "27670")
    @ExcelProperty("回复的用户编号")
    private Long replyUserId;

    @Schema(description = "回复的用户名称", example = "芋艿")
    @ExcelProperty("回复的用户名称")
    private String replyUserNickname;

    @Schema(description = "回复的内容")
    @ExcelProperty("回复的内容")
    private String replyContent;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
