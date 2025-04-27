package cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 食材分类新增/修改 Request VO")
@Data
public class FoodCategorySaveReqVO {

    @Schema(description = "分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5503")
    private Long id;

    @Schema(description = "父分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23070")
    @NotNull(message = "父分类编号不能为空")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @Schema(description = "移动端分类图", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "移动端分类图不能为空")
    private String picUrl;

    @Schema(description = "分类排序")
    private Integer sort;

    @Schema(description = "开启状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "开启状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "随便")
    private String memo;

}