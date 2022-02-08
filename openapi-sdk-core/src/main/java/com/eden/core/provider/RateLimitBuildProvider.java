package com.eden.core.provider;


import com.eden.core.param.RateLimitParam;

/**
 * 限流基础构建
 */
public interface RateLimitBuildProvider extends RequestBaseProvider{


    String determineKey(RateLimitParam limitParam);


    String buildScript();
}
