package cn.iocoder.yudao.module.meals.controller.app.food;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.meals.controller.app.food.vo.AppFoodRespVO;
import cn.iocoder.yudao.module.meals.dal.dataobject.food.FoodDO;
import cn.iocoder.yudao.module.meals.service.food.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult<AppFoodRespVO> getFood(@RequestParam("id") Long id) {
        FoodDO food = foodService.getFood(id);
        return success(BeanUtils.toBean(food, AppFoodRespVO.class));
    }
}
