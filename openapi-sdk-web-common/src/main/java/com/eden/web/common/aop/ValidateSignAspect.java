package com.eden.web.common.aop;

import com.eden.core.entity.MemberEntity;
import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.param.BaseSignParam;
import com.eden.core.resp.ResultWrap;
import com.eden.service.SdkMemberService;
import com.eden.web.common.handler.SignParamCheckHandler;
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
import java.util.Optional;

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
    SignParamCheckHandler signParamCheckHandler;

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
        log.debug("进入切面签名验证参数:{}", signParam);
        signParamCheckHandler.checkTimeStamp(signParam, time);
        List<MemberEntity> memberList = sdkMemberService.list(signParam.getAppKey());
        log.debug("进入切面签名验证memberList:{}", memberList);
        Optional.ofNullable(CollectionUtils.isEmpty(memberList))
                .ifPresent($ -> ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_MEMBER_APPKEY_ERROR));
        signParam.setSecret(memberList.get(0).getAppSecret());
        signParamCheckHandler.checkSign(signParam);
    }
}
