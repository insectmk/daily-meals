package cn.iocoder.yudao.module.meals.service.common;

import cn.iocoder.yudao.framework.common.biz.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.module.meals.dal.redis.staticdict.StaticDictDAO;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @Title: CommonServiceImpl
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.service.common
 * @Date 2025/4/29 10:48
 * @description: 常用服务实现
 */
@Service
@Validated
public class CommonServiceImpl implements CommonService {
    @Resource
    private DictDataApi dictDataApi;
    @Resource
    private StaticDictDAO staticDictDAO;

    @Override
    public List<DictDataRespDTO> getDictDataListByType(String type) {
        // 先从缓存中获取，没有就从数据库中获取
        List<DictDataRespDTO> dictDataList = staticDictDAO.get(type);
        if (dictDataList.isEmpty()) {
            // 从数据库中获取
            return dictDataApi.getDictDataList(type);
        }
        return dictDataList;
    }
}
