package cn.iocoder.yudao.module.meals.service.common;

import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;

import java.util.List;

/**
 * @Title: CommonService
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.service.common
 * @Date 2025/4/29 10:47
 * @description: 常用服务
 */
public interface CommonService {
    /**
     * 根据字典类型查询字典数据信息
     * @param type
     * @return
     */
    List<DictDataRespDTO> getDictDataListByType(String type);
}
