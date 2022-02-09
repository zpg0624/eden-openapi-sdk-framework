package com.eden.core.strategy;

import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface ExRespJsonMsgStrategy {

   void sendRespMsg(HttpServletResponse response, Throwable e);
}
