package cn.lhz.ecs.common.web.service;


import cn.lhz.ecs.common.web.service.fallback.RedisServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Neo
 * @date 2019/11/26 23:33
 */
@FeignClient(value = "ecs-redis",fallback = RedisServiceFallBack.class)
public interface RedisService
{
    @RequestMapping(value = "put",method = RequestMethod.POST)
    public  String put(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value,
                       @RequestParam(value = "seconds") long seconds);

    @RequestMapping(value = "get",method = RequestMethod.GET)
    public  String get(@RequestParam(value = "key") String key);
}