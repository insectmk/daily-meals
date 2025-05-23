package cn.iocoder.yudao.module.meals.controller.app.usercollect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户 APP - 用户获取精简收藏列表 Request VO")
@Data
public class AppUserCollectSimpleListReqVO {

    @Schema(description = "内容编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30819")
    @NotNull(message = "内容编号不能为空")
    private Long contentId;

    @Schema(description = "内容类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;

}
