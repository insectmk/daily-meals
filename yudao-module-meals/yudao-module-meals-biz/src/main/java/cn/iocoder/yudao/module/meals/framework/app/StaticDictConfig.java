package cn.iocoder.yudao.module.meals.framework.app;

import cn.iocoder.yudao.module.meals.dal.redis.staticdict.StaticDictDAO;
import cn.iocoder.yudao.module.meals.enums.DictTypeConstants;
import cn.iocoder.yudao.module.meals.enums.RecipeStatusEnum;
import cn.iocoder.yudao.module.meals.enums.RecipeTypesEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Title: DictConfig
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.framework.app
 * @Date 2025/4/29 10:59
 * @description: 静态字典配置
 */
@Component
@Slf4j
public class StaticDictConfig implements CommandLineRunner {
    @Resource
    private StaticDictDAO staticDictDAO;

    static {
        log.info("静态字典初始化");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("启动时自动执行 CommandLineRunner 的 run 方法");
        // 将自定义的静态字典加入到redis缓存中
        staticDictDAO.set(DictTypeConstants.MEALS_STATIC_RECIPE_STATUS, RecipeStatusEnum.ARRAYS); // 菜谱状态
        staticDictDAO.set(DictTypeConstants.MEALS_STATIC_RECIPE_TYPES, RecipeTypesEnum.ARRAYS); // 菜谱类型
    }
}
