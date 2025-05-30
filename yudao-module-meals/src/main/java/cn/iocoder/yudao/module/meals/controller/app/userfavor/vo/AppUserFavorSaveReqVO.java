package cn.iocoder.yudao.module.meals.controller.app.userfavor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "用户 APP - 用户收藏新增/修改 Request VO")
@Data
public class AppUserFavorSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5241")
    private Long id;

    @Schema(description = "内容编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30819")
    @NotNull(message = "内容编号不能为空")
    private Long contentId;

    @Schema(description = "内容类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;

    @Schema(description = "收藏夹ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "[26100,2343]")
    private List<Long> collectIds;

}
