package cn.iocoder.yudao.module.meals.controller.app.foodcategoy;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo.FoodCategoryListReqVO;
import cn.iocoder.yudao.module.meals.controller.admin.foodcategory.vo.FoodCategoryRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.foodcategory.FoodCategoryDO;
import cn.iocoder.yudao.module.meals.service.foodcategory.FoodCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 食材分类")
@RestController
@RequestMapping("/meals/food-category")
@Validated
public class AppFoodCategoryController {

    @Resource
    private FoodCategoryService foodCategoryService;

    @GetMapping("/list")
    @Operation(summary = "获得食材分类列表")
    @PermitAll
    public CommonResult<List<FoodCategoryRespVO>> getFoodCategoryList(@Valid FoodCategoryListReqVO listReqVO) {
        List<FoodCategoryDO> list = foodCategoryService.getFoodCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, FoodCategoryRespVO.class));
    }
}
