package com.eden.service.impl;

import com.eden.core.entity.MemberEntity;
import com.eden.service.SdkMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业用户信息
 *
 * @author eden 【】
 * @since 2019-10-29 15:09
 */

@Service
public class SdkMemberServiceImpl implements SdkMemberService {



    @Override
    public List<MemberEntity> list(String appkey) {
        return null;
    }

    @Override
    public List<MemberEntity> list() {
        return null;
    }
}
