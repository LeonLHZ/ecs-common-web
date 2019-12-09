package cn.lhz.ecs.common.web.config;

import cn.lhz.ecs.common.web.interceptor.ConstantsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Neo
 * @date 2019/11/25 23:17
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer
{

    @Autowired
    private ConstantsInterceptor constantsInterceptor;

    public InterceptorConfig()
    {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(constantsInterceptor).addPathPatterns("/**");

    }
}
