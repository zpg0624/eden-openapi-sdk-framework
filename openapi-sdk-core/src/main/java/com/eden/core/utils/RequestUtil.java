package com.eden.core.utils;

import com.eden.core.annotations.Limit;
import com.eden.core.consts.SysConsts;
import com.eden.core.enums.LimitType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 请求相关帮助方法
 *
 * @author eden 【】
 * @since 2019-11-25 14:01
 */
public class RequestUtil {


    /**
     * 获取IP地址
     *
     * @return {@link String}
     * @author eden 【】
     * @since 2019/11/25
     */
    public static String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || SysConsts.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || SysConsts.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || SysConsts.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 获取Key值
     * @param method
     * @param limitAnnotation
     * @param limitType
     * @return {@link String}
     * @author eden 【】
     * @since 2019/11/25
     */
    public static String determineKey(Method method, Limit limitAnnotation, LimitType limitType) {
        String key;
        switch (limitType) {
            case IP:
                key = RequestUtil.getIpAddress();
                break;
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            default:
                key = method.getName();
        }
        return key;
    }

    /**
     * 限流执行lua脚本
     * @return {@link String}
     * @author eden 【】
     * @since 2019/11/25
     */
    public static String buildLuaScript() {
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
