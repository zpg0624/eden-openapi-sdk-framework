package com.eden.core.strategy;


import com.eden.core.ex.ValidateParamsException;
import com.eden.core.utils.SendMsgUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class ValidParamExJsonMsgStrategy implements ExRespJsonMsgStrategy {
    @Override
    public void sendRespMsg(HttpServletResponse response, Throwable e) {
        log.info("validate args exception :{}", e);
        Optional.of(e)
                .filter(ValidateParamsException.class::isInstance)
                .map(ValidateParamsException.class::cast)
                .ifPresent(x -> SendMsgUtil.sendJsonMessage(response, x.getFailedResult()));
    }
}
