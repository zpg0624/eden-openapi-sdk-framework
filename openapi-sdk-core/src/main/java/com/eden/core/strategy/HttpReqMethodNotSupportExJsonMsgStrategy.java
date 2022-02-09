package com.eden.core.strategy;


import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.SendMsgUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class HttpReqMethodNotSupportExJsonMsgStrategy implements ExRespJsonMsgStrategy {
    @Override
    public void sendRespMsg(HttpServletResponse response, Throwable e) {
        SendMsgUtil.sendJsonMessage(response, ResultWrap.getInstance().buildFailedThenMsg(e.getMessage()));
    }
}
