package cn.iocoder.yudao.module.meals.service.statistics;


import cn.iocoder.yudao.framework.common.biz.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.module.meals.controller.app.statistics.vo.AppMsgInteractDataRespVO;

import java.util.List;

/**
 * @Title: CommonService
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.service.common
 * @Date 2025/6/30 10:47
 * @description: 统计服务
 */
public interface StatisticsService {
    /**
     * 消息统计数据
     * @param userId 用户ID
     * @return 统计结果
     */
    AppMsgInteractDataRespVO getMsgInteractData(Long userId);
}
