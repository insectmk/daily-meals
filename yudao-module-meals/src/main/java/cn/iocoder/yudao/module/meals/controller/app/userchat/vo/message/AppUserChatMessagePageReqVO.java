package cn.iocoder.yudao.module.meals.controller.app.userchat.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户APP - 用户聊天消息分页请求 Request VO")
@Data
public class AppUserChatMessagePageReqVO {

    private static final Integer LIMIT = 10;

    @Schema(description = "会话编号", example = "12580")
    private Long conversationId;

    @Schema(description = "发送时间", example = "2024-03-27 12:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @Schema(description = "接收方用户编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1320")
    @NotNull(message = "接收方用户编码不能为空")
    private Long receiverUserId;

    @Schema(description = "每次查询条数，最大值为 100", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每次查询条数不能为空")
    @Min(value = 1, message = "每次查询条数最小值为 1")
    @Max(value = 100, message = "每次查询最大值为 100")
    private Integer limit = LIMIT;

    // ========== 后端设置的参数，前端无需传递 ==========

    @Schema(description = "发送人编号", example = "24571", hidden = true)
    private Long senderUserId;
}
