package com.eden.web.common.controller;


import com.eden.core.entity.MemberEntity;
import com.eden.web.common.context.MemberContext;

public class BaseController {


    /**
     * 获取当前企业会员对象
     *
     * @return
     */
    public MemberEntity currentMember() {
        return MemberContext.current();
    }
}
