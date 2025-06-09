package cn.iocoder.yudao.module.meals.controller.app.recipemenu.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 菜谱菜单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppRecipeMenuPageReqVO extends PageParam {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "副标题")
    private String subtitle;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "菜单描述")
    private String menuDesc;

    @Schema(description = "菜单类型")
    private Integer menuType;

    @Schema(description = "菜单状态")
    private Integer menuStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
