package cn.lhz.ecs.common.web.config;

import cn.lhz.ecs.common.web.interceptor.ConstantsInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Neo
 * @date 2019/11/25 23:17
 */
@Component
public class InterceptorConfig implements WebMvcConfigurer
{
    public InterceptorConfig()
    {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new ConstantsInterceptor()).addPathPatterns("/**");
    }
}
