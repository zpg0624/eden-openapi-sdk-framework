package com.eden.web.common.controller;


import com.eden.core.consts.SysConsts;
import com.eden.core.entity.MemberEntity;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class BaseController {

    @Resource
    protected HttpServletRequest request;


    /**
     * 获取当前企业会员对象
     * @return
     */
    public MemberEntity currentMember() {
        if (Objects.nonNull(request.getAttribute(SysConsts.CURR_MEMBER))) {
            return (MemberEntity) request.getAttribute(SysConsts.CURR_MEMBER);
        }
        return null;
    }


}
