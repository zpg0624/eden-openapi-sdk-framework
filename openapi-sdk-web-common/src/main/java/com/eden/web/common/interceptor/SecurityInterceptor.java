package com.eden.web.common.interceptor;

import com.eden.core.annotations.Access;
import com.eden.core.consts.SysConsts;
import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.utils.CommonUtils;
import com.eden.core.utils.SendMsgUtil;
import com.eden.framework.entity.MemberEntity;
import com.eden.service.SdkMemberPermissionService;
import com.eden.service.SdkMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 权限安全拦截
 *
 * @author eden
 * @since 2019-10-28 10:27
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    SdkMemberService sdkMemberService;

    @Autowired
    SdkMemberPermissionService sdkMemberPermissionService;

    /**
     * 请求之前拦截
     * <p></p>
     * <p>
     * 1、数据库验证该APPid是否存在， 2、判断当前appid企业是否存在该URI路径权限 HasPermission
     *
     * @param request
     * @param response
     * @param handler
     * @return {@link boolean}
     * @author eden 【】
     * @since 2019/10/29
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader(SysConsts.CONST_HEADER_AUTH_PARAM);
        if (StringUtils.isEmpty(authHeader)) {
            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_AUTH_EMPTY);
            return false;
        }
        log.info("头部授权参数值:{}", authHeader);
        String header = CommonUtils.decoder(authHeader);
        if (StringUtils.isEmpty(header)) {
            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_DECODE_ERROR);
            return false;
        }
        String[] memberInfo = header.split("_");
        if (memberInfo.length != 2) {
            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_DECODE_ERROR);
            return false;
        }
        log.info("头部信息解析出来的会员信息为:{}", Arrays.toString(memberInfo));
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Access permission = handlerMethod.getMethod().getAnnotation(Access.class);
            if (StringUtils.isEmpty(permission)) {
                permission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Access.class);
            }
            if (StringUtils.isEmpty(permission) || !permission.required()) {
                SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_COMMON_API_DISABLE);
                return false;
            }
        }
        //TODO 获取会员信息 和权限
//        List<MemberEntity> memberList = sdkMemberService.list();
//        if (CollectionUtils.isEmpty(memberList)) {
//            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_MEMBER_APPID_ERROR);
//            return false;
//        }
//        List<MemberPermissionEntity> permissionList = sdkMemberPermissionService.list();
//        if (CollectionUtils.isEmpty(permissionList)) {
//            SendMsgUtil.sendJsonMsg(response, ResultMsgEnum.RESULT_AUTH_API_ERROR);
//            return false;
//        }

        request.setAttribute(SysConsts.CURR_MEMBER, new MemberEntity());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //忽略

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }
}
