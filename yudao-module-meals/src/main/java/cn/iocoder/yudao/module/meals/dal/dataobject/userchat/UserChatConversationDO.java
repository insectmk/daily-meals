package cn.iocoder.yudao.module.meals.dal.dataobject.userchat;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 用户会话 DO
 *
 * @author InsectMk
 */
@TableName("meals_user_chat_conversation")
@KeySequence("meals_user_chat_conversation") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChatConversationDO extends BaseDO {
    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 会话所属用户
     *
     * 关联 {@link MemberUserRespDTO#getId()}
     */
    private Long userId;

    /**
     * 最后聊天时间
     */
    private LocalDateTime lastMessageTime;
    /**
     * 最后聊天内容
     */
    private String lastMessageContent;
    /**
     * 最后发送的消息类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.meals.enums.userchat.UserChatMessageContentTypeEnum}
     */
    private Integer lastMessageContentType;

    //======================= 会话操作相关 =======================

    /**
     * 是否置顶
     */
    private Boolean pinned;
    /**
     * 用户是否可见
     *
     * false - 可见，默认值
     * true - 不可见，用户删除时设置为 true
     */
    private Boolean userDeleted;
    /**
     * 未读消息数
     *
     * 用户发送消息时增加，管理员查看后扣减
     */
    private Integer unreadMessageCount;

}
