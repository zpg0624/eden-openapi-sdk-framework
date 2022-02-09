package com.eden.core.enums;

import com.eden.core.ex.ValidateParamsException;
import com.eden.core.strategy.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.net.SocketTimeoutException;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExBindClassEnum {

    BIND_VALID_EX(ValidateParamsException.class, new ValidParamExJsonMsgStrategy()),
    BIND_ILLEGAL_EX(IllegalArgumentException.class, new IllegalArgExJsonMsgStrategy()),
    BIND_COMMON_EX(Exception.class, new CommonExJsonMsgStrategy()),
    BIND_TYPENOTSUPPORT_EX(HttpMediaTypeNotSupportedException.class, new HttpMediaTypeNotSupportExJsonMsgStrategy()),
    BIND_METHOD_NOTSUPPORT_EX(HttpRequestMethodNotSupportedException.class, new HttpReqMethodNotSupportExJsonMsgStrategy()),
    BIND_TIMEOUT_EX(SocketTimeoutException.class, new SocketTimeOutExJsonMsgStrategy()),


    ;

    private Class<?> aClass;

    private ExRespJsonMsgStrategy exRespJsonMsgStrategy;


    @SneakyThrows
    public static ExRespJsonMsgStrategy matchExStrategyClass(Class<? extends Throwable> c) {
        final ExBindClassEnum exBindClassEnum = Arrays.stream(ExBindClassEnum.values())
                .filter($ -> $.aClass.equals(c))
                .findFirst().get();
        return exBindClassEnum.getExRespJsonMsgStrategy();
    }
}
