package com.eden.web.common.provider;

import com.eden.core.param.RateLimitParam;
import com.eden.core.provider.RateLimitBuildProvider;
import org.springframework.stereotype.Component;

/**
 * Redis构建限流脚本和key
 */
@Component
public class RedisRateLimitBuildProvider implements RateLimitBuildProvider {

    /**
     * 获取Key值
     *
     * @param limitParam 具体参数请查看{@link RateLimitParam}
     * @return {@link String}
     * @author eden
     * @since 2019/11/25
     */
    @Override
    public String determineKey(RateLimitParam limitParam) {
        String key;
        switch (limitParam.getLimitAnnotation().limitType()) {
            case IP:
                key = getIpAddress();
                break;
            case CUSTOMER:
                key = limitParam.getLimitAnnotation().key();
                break;
            default:
                key = limitParam.getDefaultKey();
        }
        return key;
    }

    /**
     * 限流执行lua脚本
     *
     * @return {@link String}
     * @author eden
     * @since 2019/11/25
     */
    @Override
    public String buildScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c")
           .append("\nc = redis.call('get',KEYS[1])")
           .append("\nif c and tonumber(c) > tonumber(ARGV[1]) then")// 调用不超过最大值，则直接返回
           .append("\nreturn c;")
           .append("\nend")
           .append("\nc = redis.call('incr',KEYS[1])")// 执行计算器自加
           .append("\nif tonumber(c) == 1 then")
           .append("\nredis.call('expire',KEYS[1],ARGV[2])")// 从第一次调用开始限流，设置对应键值的过期
           .append("\nend")
           .append("\nreturn c;");
        return lua.toString();
    }


}
