package cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 食材分类列表 Request VO")
@Data
public class FoodCategoryListReqVO {

    @Schema(description = "父分类编号", example = "23070")
    private Long parentId;

    @Schema(description = "分类名称", example = "芋艿")
    private String name;

    @Schema(description = "移动端分类图", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "分类排序")
    private Integer sort;

    @Schema(description = "开启状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}