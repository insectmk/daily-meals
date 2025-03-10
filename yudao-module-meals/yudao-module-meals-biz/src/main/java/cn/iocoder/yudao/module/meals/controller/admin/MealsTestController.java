package cn.iocoder.yudao.module.meals.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @Title: MealsTestController
 * @Author InsectMk
 * @Package cn.insectmk.meals.controller.admin
 * @Date 2025/3/6 13:21
 * @description: 测试
 */
@Tag(name = "管理后台 - 每日饭菜")
@RestController
@RequestMapping("/meals/test")
@Validated
public class MealsTestController {
    @GetMapping("/get")
    @Operation(summary = "获取 test 信息")
    public CommonResult<String> get() {
        return success("true");
    }
}
