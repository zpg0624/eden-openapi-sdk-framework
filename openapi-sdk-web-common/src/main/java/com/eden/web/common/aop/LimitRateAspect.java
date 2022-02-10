package com.eden.web.common.aop;


import com.eden.core.annotations.Limit;
import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.param.RateLimitParam;
import com.eden.core.provider.RateLimitBuildProvider;
import com.eden.core.resp.ResultWrap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 计数器限流算法拦截
 *
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

    @Autowired
    RateLimitBuildProvider rateLimitBuildProvider;


    /**
     * 切入点限流
     *
     * @param pjp
     * @return {@link Object}
     * @author eden 【】
     * @since 2019/11/25
     */
    @Around("execution(public * com.eden.web.controller..*(..)) && @annotation(com.eden.core.annotations.Limit)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        final Limit limitAnnotation = Optional.of(pjp)
                .map(ProceedingJoinPoint::getSignature)
                .filter(MethodSignature.class::isInstance)
                .map(MethodSignature.class::cast)
                .map(MethodSignature::getMethod)
                .map(method -> method.getAnnotation(Limit.class))
                .orElseThrow(IllegalArgumentException::new);
        String key = rateLimitBuildProvider.determineKey(RateLimitParam.of("executeRedisDistributedRateLimit", limitAnnotation));
        RedisScript<Number> redisScript = RedisScript.of(rateLimitBuildProvider.buildScript(), Number.class);
        Number count = limitRedisTemplate.execute(redisScript, Arrays.asList(StringUtils.join(limitAnnotation.prefix(), key)),
                limitAnnotation.count(), limitAnnotation.period());
        Optional.ofNullable(count)
                .filter(c -> Objects.isNull(count) || count.intValue() > limitAnnotation.count())
                .ifPresent($ -> ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_LIMIT_RATE_ERROR));
        Object proceed = pjp.proceed();
        //TODO 以下可以进行后置增强操作
        return proceed;
    }
}