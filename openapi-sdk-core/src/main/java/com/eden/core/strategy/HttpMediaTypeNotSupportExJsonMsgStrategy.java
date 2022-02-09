package com.eden.core.strategy;



import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.ex.ValidateParamsException;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.SendMsgUtil;

import javax.servlet.http.HttpServletResponse;

public class HttpMediaTypeNotSupportExJsonMsgStrategy implements ExRespJsonMsgStrategy {
    @Override
    public void sendRespMsg(HttpServletResponse response, Throwable e) {
        SendMsgUtil.sendJsonMessage(response,
                ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_MEDIA_TYPE_ERROR));
    }
}
