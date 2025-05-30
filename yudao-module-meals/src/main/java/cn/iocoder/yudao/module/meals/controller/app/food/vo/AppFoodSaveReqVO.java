package cn.iocoder.yudao.module.meals.controller.app.food.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "用户 APP - 食材新增/修改 Request VO")
@Data
public class AppFoodSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7107")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "分类不能为空")
    private Integer foodType;

    @Schema(description = "单位", example = "1")
    private Integer foodUnit;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

}