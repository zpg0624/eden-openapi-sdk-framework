package com.eden.web.common.handler;


import com.eden.core.enums.ExBindClassEnum;
import com.eden.core.ex.ValidateParamsException;
import com.eden.core.strategy.ExProcessorContext;
import com.eden.core.strategy.ExRespJsonMsgStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.net.SocketTimeoutException;

/**
 * 全局异常拦截
 *
 * @author eden
 * @since 2019/10/28
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 参数验证全局拦截
     * <p>
     * 1、策略模式统一封装在core中，后续需要加对应的exception直接查看{@link ExBindClassEnum}
     * <p>
     * 2、实现对应的异常策略即可：请查看{@link ExRespJsonMsgStrategy}
     * @param e
     * @param response
     * @author eden
     * @since 2019/10/29
     */
    @ExceptionHandler({
            ValidateParamsException.class,
            IllegalArgumentException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            SocketTimeoutException.class,
            Exception.class
    })
    public void processException(Throwable e, HttpServletResponse response) {
        log.debug("processException 异常: ", e);
        ExProcessorContext.matchEx(e.getClass()).executeSendMsg(response, e);
    }
}
