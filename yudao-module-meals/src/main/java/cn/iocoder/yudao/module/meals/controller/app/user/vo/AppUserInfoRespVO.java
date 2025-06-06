package cn.iocoder.yudao.module.meals.controller.app.user.vo;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Title: AppUserInteractDataRespVO
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.controller.app.user.vo
 * @Date 2025/6/6 10:28
 * @description: 用户信息
 */
@Schema(description = "用户APP - 用户信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppUserInfoRespVO {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "27498")
    @ExcelProperty("用户ID")
    private Long id;
    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "InsectMk")
    @ExcelProperty("用户昵称")
    private String nickname;
    /**
     * 帐号状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    @Schema(description = "帐号状态", example = "1")
    @ExcelProperty("帐号状态")
    private Integer status;
    /**
     * 用户头像
     */
    @Schema(description = "用户头像", example = "https://os.insectmk.cn/head.jpg")
    @ExcelProperty("用户头像")
    private String avatar;
    /**
     * 手机
     */
    @Schema(description = "手机", example = "13144445555")
    @ExcelProperty("手机")
    private String mobile;
    /**
     * 创建时间（注册时间）
     */
    @Schema(description = "创建时间（注册时间）")
    @ExcelProperty("创建时间（注册时间）")
    private LocalDateTime createTime;

    // ========== 其它信息 ==========

    /**
     * 会员级别编号
     */
    @Schema(description = "会员级别编号", example = "1")
    @ExcelProperty("会员级别编号")
    private Long levelId;

    /**
     * 积分
     */
    @Schema(description = "积分", example = "27498")
    @ExcelProperty("积分")
    private Integer point;
}
