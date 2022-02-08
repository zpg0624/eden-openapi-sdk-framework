package com.eden.service;

import com.eden.core.entity.MemberEntity;

import java.util.List;

/**
 * 企业信息
 *
 * @author eden
 * @since 2019/10/30
 */
public interface SdkMemberService {


    /**
     * 企业信息列表
     *
     * @since 2019/10/30
     */
    List<MemberEntity> list(String appkey);

}
