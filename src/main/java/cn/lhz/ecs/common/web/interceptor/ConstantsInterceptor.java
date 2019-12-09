package cn.lhz.ecs.common.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;

/**
 * 初始化常亮拦截器
 * @author Neo
 * @date 2019/11/25 22:53
 */
@Configuration
public class ConstantsInterceptor implements HandlerInterceptor
{
    private static final String HOST_CDN = "http://www.lhz520.cn";
    public ConstantsInterceptor()
    {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception
    {
        //restController 中modelAndView为空
        if (modelAndView!=null){
            modelAndView.addObject("hostname",HOST_CDN);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception
    {

    }
}
