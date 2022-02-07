package com.eden.core.utils;


import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;


/**
 * 签名验证帮助类
 *
 * @author eden 【】
 * @since 2019-08-01 17:14
 */
public class SignValidateUtils {


    /**
     * 签名字符串拼接
     *
     * @param params
     * @return {@link String}
     * @author eden 【】
     * @since 2019/8/12
     */
    public static String sortMapByKey(Map<String, Object> params) {
        String sign = Optional.ofNullable(params)
                .map(signParams -> ofBuildSignParams(params))
                .orElseThrow(IllegalArgumentException::new);
        return sign;
    }


    public static boolean validateTimeStamp(Long targetTimeStamp, Integer expiredMinuteTime) {
        if (Math.abs(Instant.now().toEpochMilli() - targetTimeStamp) > (expiredMinuteTime * 60 * 1000)) {
            return false;
        }
        return true;
    }


    private static String ofBuildSignParams(Map<String, Object> params) {
        Map<String, Object> sortMap = new TreeMap<>();
        sortMap.putAll(params);
        sortMap.remove("sign");
        List<String> list = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        sortMap.keySet()
                .stream()
                .filter(x -> Objects.nonNull(sortMap.get(x)))
                .iterator()
                .forEachRemaining(key -> list.add(String.join("=", key, sortMap.get(key).toString())));
        int length = list.size() - 1;
        IntStream.range(0, length).forEach(x -> str.append(list.get(x)).append("&"));
        str.append(list.get(length));
        return str.toString();
    }



}
