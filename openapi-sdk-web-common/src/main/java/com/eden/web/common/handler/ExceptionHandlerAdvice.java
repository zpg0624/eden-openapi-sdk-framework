package com.eden.web.common.handler;


import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.ex.ValidateParamsException;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.SendMsgUtil;
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
     *
     * @param e
     * @param response
     * @author eden 
     * @since 2019/10/29
     */
    @ExceptionHandler(ValidateParamsException.class)
    public void processValidateException(ValidateParamsException e, HttpServletResponse response) {
        log.info("validate params exception :{}", e);
        SendMsgUtil.sendJsonMessage(response, e.getFailedResult());
    }

    /**
    * 拦截参数错误异常
     *
    * @param e
    * @param response 
    * @author eden 
    * @since 2019/10/29
    */
    @ExceptionHandler(IllegalArgumentException.class)
    public void processIllegalArgException(IllegalArgumentException e,
                                           HttpServletResponse response) {
        log.info("Illegal args exception :{}", e);
        SendMsgUtil.sendJsonMessage(response,
            ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_AUTH_PARAM_ERROR));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public void processHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e,
                                           HttpServletResponse response) {
        log.info("mediaType args exception :{}", e);
        SendMsgUtil.sendJsonMessage(response,
            ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_MEDIA_TYPE_ERROR));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void processHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                              HttpServletResponse response) {
        log.info("HttpRequestMethodNotSupportedException args exception :{}", e);
        SendMsgUtil.sendJsonMessage(response,ResultWrap.getInstance().buildFailedThenMsg(e.getMessage()));
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public void processSocketTimeoutException(SocketTimeoutException e,
                                              HttpServletResponse response) {
        log.info("SocketTimeout args exception :{}", e);
        SendMsgUtil.sendJsonMessage(response,
            ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_TIMEOUT_ERROR));
    }

    /**
     * 拦截参统一错误异常
     *
     * @param e
     * @param response
     * @author eden
     * @since 2019/10/29
     */
    @ExceptionHandler(Exception.class)
    public void processException(Exception e, HttpServletResponse response) {
        log.info("exception :{}", e);
        SendMsgUtil.sendJsonMessage(response,
            ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_COMMON_API_SYS_ERROR));
    }
}
