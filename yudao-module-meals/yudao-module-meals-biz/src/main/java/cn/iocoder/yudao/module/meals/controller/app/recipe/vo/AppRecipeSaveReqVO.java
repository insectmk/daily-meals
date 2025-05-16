package cn.iocoder.yudao.module.meals.controller.app.recipe.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipe.RecipeFoodDO;

@Schema(description = "用户 APP - 菜谱新增/修改 Request VO")
@Data
public class AppRecipeSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23107")
    private Long id;

    @Schema(description = "用户编号", example = "21961")
    private Long userId;

    @Schema(description = "名称", example = "李四")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "简介")
    @NotNull(message = "简介不能为空")
    private String recipeDesc;

    @Schema(description = "教程")
    @NotNull(message = "教程不能为空")
    private String recipeStep;

    @Schema(description = "菜谱类型", example = "2")
    private Integer recipeType;

    @Schema(description = "菜谱分类", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "[22, 11]")
    @NotNull(message = "菜谱分类不能为空")
    private List<Integer> recipeCategory;

    @Schema(description = "烹饪难度")
    private Integer recipeLevel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注", example = "你说的对")
    private String memo;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "菜谱封面图", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/xx.png")
    private String picUrl;

    @Schema(description = "菜谱轮播图", requiredMode = Schema.RequiredMode.REQUIRED, example = "[https://www.iocoder.cn/xx.png, https://www.iocoder.cn/xxx.png]")
    private List<String> sliderPicUrls;

    @Schema(description = "菜谱食材", requiredMode = Schema.RequiredMode.REQUIRED, example = "[{id: 1, recipeId: 1, foodId: 1, amount: 0.2, memo: '测试'},{id: 1, recipeId: 1, foodId: 1, amount: 0.2, memo: '测试'}]")
    private List<AppRecipeFoodSaveReqVO> foods;
}
