package com.eden.web.common.aop;

import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.resp.ResultWrap;
import com.eden.framework.entity.MemberEntity;
import com.eden.framework.param.BaseSignParam;
import com.eden.service.SdkMemberService;
import com.eden.web.common.handler.SignHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 切面验证签名
 *
 * @author eden 【】
 * @since 2019-10-28 16:11
 */

@Slf4j
@Component
@Aspect
@Order(2)
public class ValidateSignAspect {

    @Value("${sign.expiredminutetime}")
    String time;


    @Autowired
    SignHandlerAdvice signHandlerAdvice;

    @Autowired
    SdkMemberService sdkMemberService;


    /**
     * 切入controller校验签名是否正确
     * <pre>
     *       1、验证时间戳是否过期 2、解析Controller参数；验证签名
     *   </pre>
     *
     * @param joinPoint
     * @author eden 【】
     * @since 2019/10/28
     */
    @Before("execution(public * com.eden.service.controller..*(..)) && @annotation(org.springframework.validation.annotation.Validated) ")
    public void doBefore(JoinPoint joinPoint) {
        Object targetParam = Arrays.stream(joinPoint.getArgs())
                                                    .filter(x -> x instanceof BaseSignParam)
                                                    .findAny()
                                                    .orElseThrow(IllegalArgumentException::new);
        BaseSignParam signParam = (BaseSignParam) targetParam;
        log.info("进入切面签名验证参数:{}", signParam);
        signHandlerAdvice.checkTimeStamp(signParam, time);
        String appKey = signParam.getAppKey();
        List<MemberEntity> memberList = sdkMemberService.list(appKey);
        log.info("进入切面签名验证memberList:{}", memberList);
        if (CollectionUtils.isEmpty(memberList)) {
            ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_MEMBER_APPKEY_ERROR);
        }
        signParam.setSecret(memberList.get(0).getAppSecret());
        signHandlerAdvice.checkSign(signParam);
    }
}
