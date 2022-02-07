package com.eden.web.common.aop;


import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.resp.ResultWrap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

/**
 * 切面验证参数
 *
 * @author eden 【】
 * @since 2019-10-28 16:11
 */

@Slf4j
@Component
@Aspect
@Order(1)
public class ValidateParamAspect  {



    /**
    *   切入controller校验参数是否正确
     *   
     * @param joinPoint 
    * @author eden 【】
    * @since 2019/10/28
    */
    @Before("execution(public * com.eden.web.controller..*(..)) && @annotation(org.springframework.validation.annotation.Validated)")
    public void doBefore(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
              .filter(x -> x instanceof BindingResult)
              .map(x -> (BindingResult) x)
              .filter(x -> x.hasErrors())
              .findAny()
              .ifPresent(x -> ResultWrap.getInstance()
                                        .buildFailed(ResultMsgEnum.RESULT_AUTH_PARAM_ERROR)
                                        .buildFailedMsg(x.getFieldError().getDefaultMessage()));
    }

}
