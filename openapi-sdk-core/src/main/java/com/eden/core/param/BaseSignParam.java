package com.eden.core.param;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 签名参数基础类
 *
 * @author eden 【】
 * @since 2019-10-29 10:05
 */
@Getter
@Setter
@ToString
public class BaseSignParam implements Serializable {

    /**
     * 时间戳
     */
    @NotNull(message = "timestamp cannot be empty")
    private Long timestamp;

    /**
     * 随机字符串
     */
    @NotNull(message = "nonceStr cannot be empty")
    @Length(min = 5, message = "nonceStr must more than five")
    private String nonceStr;

    /**
     * 签名
     */
    @NotNull(message = "sign cannot be empty")
    private String sign;

    /**
     * 应用的key值
     */
    @NotNull(message = "appKey cannot be empty")
    private String appKey;


    /**
     * 密钥
     */
    private String secret;

}
