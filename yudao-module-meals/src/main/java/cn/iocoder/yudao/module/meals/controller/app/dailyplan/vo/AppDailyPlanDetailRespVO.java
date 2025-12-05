package cn.iocoder.yudao.module.meals.controller.app.dailyplan.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.module.meals.controller.app.dailyplanitem.vo.AppDailyPlanItemDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "用户 APP - 菜谱计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppDailyPlanDetailRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10060")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28829")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "计划日")
    @ExcelProperty("计划日")
    private LocalDateTime planDate;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String memo;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    // **************明细信息*************
    private List<AppDailyPlanItemDetailRespVO> items;

}
