package cn.iocoder.yudao.module.meals.controller.app.food;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.admin.food.vo.FoodSimpleRespVO;
import cn.iocoder.yudao.module.meals.controller.app.food.vo.AppFoodRespVO;
import cn.iocoder.yudao.module.meals.convert.food.FoodConvert;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.service.food.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 食材")
@RestController
@RequestMapping("/meals/food")
@Validated
public class AppFoodController {
    @Resource
    private FoodService foodService;

    @GetMapping("/get")
    @Operation(summary = "获得食材")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PermitAll
    public CommonResult<AppFoodRespVO> getFood(@RequestParam("id") Long id) {
        FoodDO food = foodService.getFood(id);
        return success(BeanUtils.toBean(food, AppFoodRespVO.class));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取食材精简信息列表", description = "主要用于前端的下拉选项")
    @PermitAll
    public CommonResult<List<FoodSimpleRespVO>> getSimpleFoodList() {
        // 获用户列表，只要开启状态的
        List<FoodDO> list = foodService.getFoodList();
        return success(FoodConvert.INSTANCE.convertSimpleList(list));
    }
}
