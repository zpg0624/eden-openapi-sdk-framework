package com.eden.web.common.aop;


import com.eden.core.annotations.Limit;
import com.eden.core.enums.LimitType;
import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
*计数器限流算法拦截
* @author eden 【】
* @since 2019/11/25
*/
@Aspect
@Component
@Slf4j
@Order(0)
public class LimitRateAspect {

    @Autowired
    RedisTemplate<String, Serializable> limitRedisTemplate;

    /**
    * 切入点限流
    * @param pjp
    * @return {@link Object}
    * @author eden 【】
    * @since 2019/11/25
    */
    @Around("execution(public * com.eden.web.controller..*(..)) && @annotation(com.eden.core.annotations.Limit)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();
        String key = RequestUtil.determineKey(method, limitAnnotation, limitType);
        List<String> keys = Arrays.asList(StringUtils.join(limitAnnotation.prefix(), key));
        String luaScript =  RequestUtil.buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
        log.info("Access try count is {} for name={} and key = {}", count, name, key);
        if (Objects.isNull(count) || count.intValue() > limitCount) {
            ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_LIMIT_RATE_ERROR);
        }
        Object proceed = pjp.proceed();
        //以下可以进行后置增强操作

        return proceed;
    }




}