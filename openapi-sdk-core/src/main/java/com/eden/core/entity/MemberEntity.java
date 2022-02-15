package com.eden.core.entity;


import lombok.*;


/**
 * 企业
 *
 * @author eden
 * @since 2019-10-29 15:11
 */
@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "getInstance")
public class MemberEntity    {

    private String username;

    private String password;

    private String name;

    private String code;

    private String appId;

    private String appKey;

    private String appSecret;

}
