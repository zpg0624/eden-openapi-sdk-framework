package com.eden.core.strategy;


import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.SendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class CommonExJsonMsgStrategy implements ExRespJsonMsgStrategy {
    @Override
    public void sendRespMsg(HttpServletResponse response, Throwable e) {
        log.info("common exception:{}", Arrays.stream(ExceptionUtils.getRootCauseStackTrace(e)).collect(Collectors.toList()));
        SendMsgUtil.sendJsonMessage(response,
                ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_COMMON_API_SYS_ERROR));
    }
}
