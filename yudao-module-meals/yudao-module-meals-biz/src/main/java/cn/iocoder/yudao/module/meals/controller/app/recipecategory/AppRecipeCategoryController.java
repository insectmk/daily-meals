package cn.iocoder.yudao.module.meals.controller.app.recipecategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.recipecategory.vo.RecipeCategoryListReqVO;
import cn.iocoder.yudao.module.meals.controller.admin.recipecategory.vo.RecipeCategoryRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.recipecategory.RecipeCategoryDO;
import cn.iocoder.yudao.module.meals.service.recipecategory.RecipeCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 菜谱分类")
@RestController
@RequestMapping("/meals/recipe-category")
@Validated
public class AppRecipeCategoryController {

    @Resource
    private RecipeCategoryService recipeCategoryService;

    @GetMapping("/list")
    @Operation(summary = "获得菜谱分类列表")
    public CommonResult<List<RecipeCategoryRespVO>> getRecipeCategoryList(@Valid RecipeCategoryListReqVO listReqVO) {
        List<RecipeCategoryDO> list = recipeCategoryService.getRecipeCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, RecipeCategoryRespVO.class));
    }
}
