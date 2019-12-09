package cn.lhz.ecs.common.web.interceptor;


import cn.lhz.common.entity.User;
import cn.lhz.common.utils.*;

import cn.lhz.ecs.common.web.constants.Constant;
import cn.lhz.ecs.common.web.service.RedisService;
import cn.lhz.ecs.common.web.utils.HttpServletUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;

/**
 * @author Neo
 * @date 2019/12/4 21:28
 */
@Component
public class SsoInterceptor implements HandlerInterceptor
{

    @Autowired
    private RedisService redisService;

    @Value(value = "${hosts.sso}")
    private String  HOSTS_SSO;
    public SsoInterceptor()
    {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String token = CookieUtils.getCookieValue(request, Constant.COOKIE_TOKEN);
        if (StringUtils.isBlank(token))
        {
            response.sendRedirect(String.format("%s/login?url=%s",HOSTS_SSO, HttpServletUtils.getFullPath(request)));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        if(user!=null){
            if(modelAndView!=null){
                modelAndView.addObject(Constant.SESSION_USER,user);
            }
        }else {
            String token = CookieUtils.getCookieValue(request,  Constant.COOKIE_TOKEN);

            if (StringUtils.isNotBlank(token)){
                String loginCode = redisService.get(token);
                if (StringUtils.isNotBlank(loginCode)){
                    String json = redisService.get(loginCode);
                    if (StringUtils.isNotBlank(json)){
                        user = MapperUtils.json2pojo(json, User.class);
                        request.getSession().setAttribute(Constant.SESSION_USER,user);
                        if(modelAndView!=null){
                                       modelAndView.addObject(Constant.SESSION_USER,user);
                                   }
                    }

                }
            }

        }
        //二次确认
        if (user==null){
            response.sendRedirect(String.format("%s/login?url=%s",HOSTS_SSO, HttpServletUtils.getFullPath(request)));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception
    {

    }
}
