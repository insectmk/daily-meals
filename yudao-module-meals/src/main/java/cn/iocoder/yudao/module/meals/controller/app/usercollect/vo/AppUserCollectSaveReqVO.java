package cn.iocoder.yudao.module.meals.controller.app.usercollect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.userfavor.UserFavorDO;

@Schema(description = "用户 APP - 用户收藏夹新增/修改 Request VO")
@Data
public class AppUserCollectSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1498")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28613")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "内容类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;

    @Schema(description = "收藏夹名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "收藏夹名称不能为空")
    private String collectName;

    @Schema(description = "封面图", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "封面图不能为空")
    private String picUrl;

    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "简介不能为空")
    private String collectDesc;

    @Schema(description = "收藏夹状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "收藏夹状态不能为空")
    private Integer collectStatus;

    @Schema(description = "用户收藏列表")
    private List<UserFavorDO> userFavors;

}