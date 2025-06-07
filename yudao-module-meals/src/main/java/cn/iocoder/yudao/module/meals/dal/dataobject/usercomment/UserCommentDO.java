package cn.iocoder.yudao.module.meals.dal.dataobject.usercomment;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论 DO
 *
 * @author InsectMk
 */
@TableName(value = "meals_user_comment", autoResultMap = true)
@KeySequence("meals_user_comment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentDO extends BaseDO {

    /**
     * 评论编号，主键自增
     */
    @TableId
    private Long id;
    /**
     * 评论人编号
     */
    private Long userId;
    /**
     * 评价人名称
     */
    private String userNickname;
    /**
     * 评价人头像
     */
    private String userAvatar;
    /**
     * 内容编号
     */
    private Long contentId;
    /**
     * 内容类型
     */
    private Integer contentType;
    /**
     * 内容所属人
     */
    private Long contentUserId;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 是否内容作者
     */
    private Boolean commentAuthor;
    /**
     * 是否已读
     */
    private Boolean userRead;
    /**
     * 评论图片地址数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private String[] picUrls;
    /**
     * 回复的用户编号
     */
    private Long replyUserId;
    /**
     * 回复的用户名称
     */
    private String replyUserNickname;
    /**
     * 回复的内容
     */
    private String replyContent;


}
