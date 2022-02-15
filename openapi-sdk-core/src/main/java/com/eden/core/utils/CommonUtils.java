package com.eden.core.utils;

import com.eden.core.consts.SysConsts;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

/**
 * 工具类
 *
 * @author eden 【】
 * @since 2019-10-28 13:50
 */
@Slf4j
public class CommonUtils {

    public static String[] decoder(String encoderStr) {
        encoderStr = encoderStr.replace(SysConsts.USER_AUTH_BASIC, "").trim();
        byte[] decode = Base64.getDecoder().decode(encoderStr);
        try {
            return new String(decode, "UTF-8").split(":");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encoder(String username, String password) {
        final String encodeToString = Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());
        String base64Str = String.format("%s %s", SysConsts.USER_AUTH_BASIC,encodeToString);
        return base64Str;
    }

    public static String generateUserName(String prefix) {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix).append(hashCodeV);

        return stringBuilder.toString();
    }

    public static String generateUUID() {
        final String s = UUID.randomUUID().toString();
        return s.replace("-", "");
    }




}
