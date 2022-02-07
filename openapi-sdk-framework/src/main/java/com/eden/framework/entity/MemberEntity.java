package com.eden.framework.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 企业
 *
 * @author eden 【】
 * @since 2019-10-29 15:11
 */
@Getter
@Setter
@ToString
public class MemberEntity    {

    private String name;

    private String code;

    private String appId;

    private String appKey;

    private String appSecret;

}
