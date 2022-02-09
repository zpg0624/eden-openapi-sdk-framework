package com.eden.core.strategy;



import com.eden.core.ex.ValidateParamsException;
import com.eden.core.utils.SendMsgUtil;

import javax.servlet.http.HttpServletResponse;

public class ValidParamExJsonMsgStrategy implements ExRespJsonMsgStrategy {
    @Override
    public void sendRespMsg(HttpServletResponse response, Throwable e) {
        SendMsgUtil.sendJsonMessage(response, ((ValidateParamsException)e).getFailedResult());
    }
}
