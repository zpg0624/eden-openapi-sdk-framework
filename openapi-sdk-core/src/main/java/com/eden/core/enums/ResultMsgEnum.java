package com.eden.core.enums;

import lombok.Getter;

/**
 * 结果信息枚举
 *
 * @author eden
 * @since 2019-10-28 11:06
 */
@Getter
public enum ResultMsgEnum {

    /**
     * 系统成功
     */

    RESULT_COMMON_SUCCESS(20001, "interface call success", "接口调用成功"),


    /**
     * 系统异常信息
     */
    RESULT_COMMON_API_DISABLE(30002, "interface disable calls", "该接口已禁止调用"),


    /**
     * 客户端请求异常
     */
    RESULT_AUTH_PARAM_ERROR(40000, "param is error", "参数错误"),

    RESULT_AUTH_INVALID(40001, "header authentication information is invalid", "头部认证信息不合法"),

    RESULT_DECODE_ERROR(40002, "authorization decode error", "认证信息解析错误，请核对"),

    RESULT_AUTH_ERROR(40003, "invalid authorization", "认证信息不合法"),

    RESULT_TIME_EXPIRED(40004, "timestamp is expired", "时间戳过期,请核对时区或者时间"),

    RESULT_SIGN_ERROR(40005, "sign is error", "签名错误"),

    RESULT_MEMBER_APPKEY_ERROR(40006, "member app key information does not exist", "查询不到企业信息,请联系商务人员或者核对APP key值"),


    RESULT_MEMBER_APPID_ERROR(40007, "member appid information does not exist", "查询不到企业信息，请联系商务人员或者核对APPID"),


    RESULT_AUTH_API_ERROR(40008, "enterprise information was not found or the interface was not allowed to be called", "未找到该企业信息或者接口已不允许调用"),


    RESULT_MEDIA_TYPE_ERROR(40009, "content type not supported", "当前请求类型不支持，请核对是否为application/json"),

    RESULT_LIMIT_RATE_ERROR(40010, "This interface is accessed too frequently", "接口访问太频繁，已限流"),

    RESULT_TIMEOUT_ERROR(40011, "Request service timed out, please try again later", "请求服务超时"),


    /**
     * 服务器统一异常
     */
    RESULT_COMMON_API_SYS_ERROR(50000, "system is error", "系统异常"),

    ;

    private Integer code;
    private String msg;
    private String desc;

    ResultMsgEnum(Integer code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

}
