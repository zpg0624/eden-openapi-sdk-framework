package com.eden.web.common.provider;

import com.eden.core.annotations.Access;
import com.eden.core.consts.SysConsts;
import com.eden.core.entity.MemberEntity;
import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.ex.ValidateParamsException;
import com.eden.core.provider.SecurityDetermineInfoProvider;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.CommonUtils;
import com.eden.service.SdkMemberPermissionService;
import com.eden.service.SdkMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@Slf4j
public class BeforeSecurityDetermineInfoProvider extends SecurityDetermineInfoProvider {

    @Autowired
    SdkMemberService sdkMemberService;

    @Autowired
    SdkMemberPermissionService sdkMemberPermissionService;


    @Override
    public void determineMemberAccess(HttpServletRequest request, HttpServletResponse response) {
        //TODO 获取会员信息 和权限
        Optional.ofNullable(sdkMemberService.list())
                .filter($ -> !CollectionUtils.isEmpty($))
                .map(__ -> sdkMemberPermissionService.list())
                .filter($ -> !CollectionUtils.isEmpty($))
                .orElseThrow(() -> new ValidateParamsException(ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_AUTH_API_ERROR)));
        //TODO 加入当前获取的会员信息到request中供其他地方获取。
        request.setAttribute(SysConsts.CURR_MEMBER, new MemberEntity());
    }

    @Override
    public void determinePermissionAnnotation(HttpServletResponse response, Object handler) {
        Optional.of(handler)
                .filter(HandlerMethod.class::isInstance)
                .map(HandlerMethod.class::cast)
                .map(handlerMethod -> handlerMethod.getMethodAnnotation(Access.class))
                .filter(Access::required)
                .orElseThrow(() -> new ValidateParamsException(ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_COMMON_API_DISABLE)));
    }


    @Override
    public void determineHeader(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getHeader(SysConsts.CONST_HEADER_AUTH_PARAM))
                .filter(org.apache.commons.lang3.StringUtils::isNotBlank)
                .map(CommonUtils::decoder)
                .filter(org.apache.commons.lang3.StringUtils::isNotBlank)
                .map(header -> header.split(SysConsts.UNDERSCORE_SEPARATOR))
                .filter($ -> $.length == SysConsts.HEADER_DEC_COUNT)
                .orElseThrow(() -> new ValidateParamsException(ResultWrap.getInstance().buildFailed(ResultMsgEnum.RESULT_AUTH_INVALID)));
    }
}
