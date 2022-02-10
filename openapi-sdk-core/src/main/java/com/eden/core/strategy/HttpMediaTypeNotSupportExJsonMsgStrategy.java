package com.eden.core.strategy;



import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.ex.ValidateParamsException;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.SendMsgUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class HttpMediaTypeNotSupportExJsonMsgStrategy implements ExRespJsonMsgStrategy {
    @Override
    public void sendRespMsg(HttpServletResponse response, Throwable e) {
        log.info("HttpMediaTypeNotSupport args exception:", e);
        SendMsgUtil.sendJsonMessage(response,
                ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_MEDIA_TYPE_ERROR));
    }
}
