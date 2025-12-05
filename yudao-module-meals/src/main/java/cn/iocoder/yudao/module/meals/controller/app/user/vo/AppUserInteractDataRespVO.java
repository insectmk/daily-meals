package cn.iocoder.yudao.module.meals.controller.app.user.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Title: AppUserInteractDataRespVO
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.controller.app.user.vo
 * @Date 2025/6/6 10:28
 * @description: 用户互动数据，关注数、粉丝数、获赞、收藏量
 */
@Schema(description = "用户APP - 用户互动数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppUserInteractDataRespVO {
    @Schema(description = "关注数", example = "27498")
    @ExcelProperty("关注数")
    private Long follows;

    @Schema(description = "粉丝数", example = "27498")
    @ExcelProperty("粉丝数")
    private Long fans;

    @Schema(description = "获赞", example = "27498")
    @ExcelProperty("获赞")
    private Long likes;

    @Schema(description = "收藏量", example = "27498")
    @ExcelProperty("收藏量")
    private Long collects;

    @Schema(description = "发布菜谱数", example = "27498")
    @ExcelProperty("发布菜谱数")
    private Long recipes;
}
