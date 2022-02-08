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
        switch (limitParam.getLimitType()) {
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
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }
}
