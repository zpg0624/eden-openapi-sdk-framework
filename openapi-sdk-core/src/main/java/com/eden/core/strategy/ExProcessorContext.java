package com.eden.core.strategy;


import com.eden.core.enums.ExBindClassEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class ExProcessorContext {

    private ExRespJsonMsgStrategy exRespJsonMsgStrategy;


    @SneakyThrows
    public static ExProcessorContext matchEx(Class<? extends Throwable> c) {
        final ExRespJsonMsgStrategy exRespJsonMsgStrategy = ExBindClassEnum.matchExStrategyClass(c);
        return ExProcessorContext.of(exRespJsonMsgStrategy);
    }


    @SneakyThrows
    public void executeSendMsg(HttpServletResponse response, Throwable e) {
        exRespJsonMsgStrategy.sendRespMsg(response, e);
    }
}
