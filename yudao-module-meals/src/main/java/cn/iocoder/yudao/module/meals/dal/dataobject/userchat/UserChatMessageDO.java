package cn.iocoder.yudao.module.meals.dal.dataobject.userchat;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 用户消息 DO
 *
 * @author InsectMk
 */
@TableName("meals_user_chat_message")
@KeySequence("meals_user_chat_message") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChatMessageDO extends BaseDO {
    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 会话编号
     *
     * 关联 {@link UserChatConversationDO#getId()}
     */
    private Long conversationId;

    /**
     * 发送人编号
     *
     * 存储的是用户编号
     */
    private Long senderUserId;
    /**
     * 接收人编号
     *
     * 存储的是用户编号
     */
    private Long receiverUserId;
    /**
     * 消息类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.meals.enums.userchat.UserChatMessageContentTypeEnum}
     */
    private Integer contentType;
    /**
     * 消息
     */
    private String content;

    //======================= 消息相关状态 =======================

    /**
     * 是/否已读
     */
    private Boolean readStatus;

}
