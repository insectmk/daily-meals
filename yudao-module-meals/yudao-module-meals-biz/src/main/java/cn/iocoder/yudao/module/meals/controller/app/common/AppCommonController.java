package cn.iocoder.yudao.module.meals.controller.app.common;

import cn.iocoder.yudao.framework.common.biz.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.meals.service.common.CommonService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "用户 APP - 通用")
@RestController
@RequestMapping("/meals/common")
@Validated
public class AppCommonController {
    @Resource
    private CommonService commonService;

    @GetMapping("/dict-data/type")
    @Operation(summary = "根据字典类型查询字典数据信息")
    @PermitAll
    public CommonResult<List<DictDataRespDTO>> getDictDataListByType(@RequestParam("type") String type) {
        return success(commonService.getDictDataListByType(type));
    }
}
