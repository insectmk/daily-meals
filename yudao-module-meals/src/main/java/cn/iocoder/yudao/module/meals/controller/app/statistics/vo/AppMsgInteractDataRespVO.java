package cn.iocoder.yudao.module.meals.controller.app.statistics.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Title: AppMsgInteractDataRespVO
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.controller.app.statistics.vo
 * @Date 2025/6/30 14:55
 * @description: 消息数据
 */
@Schema(description = "用户 APP - 统计 消息统计数据 Request VO")
@Data
@ToString(callSuper = true)
public class AppMsgInteractDataRespVO {
    @Schema(description = "获赞", example = "27498")
    @ExcelProperty("获赞")
    private Long likes;

    @Schema(description = "收藏量", example = "27498")
    @ExcelProperty("收藏量")
    private Long collects;

    @Schema(description = "收到的评论", example = "27498")
    @ExcelProperty("收到的评论")
    private Long comments;

    @Schema(description = "回复我的", example = "27498")
    @ExcelProperty("回复我的")
    private Long receives;
}
