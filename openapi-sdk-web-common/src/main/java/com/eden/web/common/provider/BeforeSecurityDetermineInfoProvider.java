package com.eden.web.common.provider;

import com.eden.core.annotations.Access;
import com.eden.core.consts.SysConsts;
import com.eden.core.entity.MemberEntity;
import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.provider.SecurityDetermineInfoProvider;
import com.eden.core.utils.CommonUtils;
import com.eden.core.utils.SendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class BeforeSecurityDetermineInfoProvider implements SecurityDetermineInfoProvider {


    @Override
    public boolean determineMemberAccess(HttpServletRequest request, HttpServletResponse response) {
        //TODO 获取会员信息 和权限
//        List<MemberEntity> memberList = sdkMemberService.list();
//        if (CollectionUtils.isEmpty(memberList)) {
//            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_MEMBER_APPID_ERROR);
//            return true;
//        }
//        List<MemberPermissionEntity> permissionList = sdkMemberPermissionService.list();
//        if (CollectionUtils.isEmpty(permissionList)) {
//            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_AUTH_API_ERROR);
//            return true;
//        }
//
        request.setAttribute(SysConsts.CURR_MEMBER, new MemberEntity());
        return false;
    }

    @Override
    public boolean determinePermissionAnnotation(HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Access permission = handlerMethod.getMethod().getAnnotation(Access.class);
            if (StringUtils.isEmpty(permission)) {
                permission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Access.class);
            }
            if (StringUtils.isEmpty(permission) || !permission.required()) {
                SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_COMMON_API_DISABLE);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean determineHeader(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(SysConsts.CONST_HEADER_AUTH_PARAM);
        if (StringUtils.isEmpty(authHeader)) {
            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_AUTH_EMPTY);
            return true;
        }
        log.info("头部授权参数值:{}", authHeader);
        String header = CommonUtils.decoder(authHeader);
        if (StringUtils.isEmpty(header)) {
            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_DECODE_ERROR);
            return true;
        }
        String[] memberInfo = header.split("_");
        if (memberInfo.length != 2) {
            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_DECODE_ERROR);
            return true;
        }
        return false;
    }
}
