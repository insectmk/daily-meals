package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户APP - 评论新增/修改 Request VO")
@Data
public class AppRecipeCommentSaveReqVO {

    @Schema(description = "评论编号，主键自增", requiredMode = Schema.RequiredMode.REQUIRED, example = "3522")
    private Long id;

    @Schema(description = "菜谱编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10501")
    @NotNull(message = "菜谱编号不能为空")
    private Long recipeId;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "评论内容不能为空")
    private String commentContent;

    @Schema(description = "是否内容作者", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean commentAuthor;

    @Schema(description = "评论图片地址数组")
    private String[] picUrls;

    @Schema(description = "回复的用户编号", example = "27670")
    private Long replyUserId;

    @Schema(description = "回复的用户名称", example = "芋艿")
    private String replyUserNickname;

    @Schema(description = "回复的内容")
    private String replyContent;

}
