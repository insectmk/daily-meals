package cn.iocoder.yudao.module.meals.framework.web.config;

import cn.iocoder.yudao.framework.swagger.config.YudaoSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * meals 模块的 web 组件的 Configuration
 *
 * @author InsectMk
 */
@Configuration(proxyBeanMethods = false)
public class MealsWebConfiguration {

    /**
     * meals 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi mealsGroupedOpenApi() {
        return YudaoSwaggerAutoConfiguration.buildGroupedOpenApi("meals");
    }

}
