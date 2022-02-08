package com.eden.service;

import com.eden.core.entity.MemberPermissionEntity;


import java.util.List;

/**
 * 企业会员接口权限
 *
 * @author eden
 * @since 2019/10/30
 */
public interface SdkMemberPermissionService {


    /**
     * 获取会员下的接口信息
     *
     * @since 2019/10/30
     */
    List<MemberPermissionEntity> list();

}
