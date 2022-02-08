package com.eden.web.common.interceptor;

import com.eden.core.annotations.Access;
import com.eden.service.SdkMemberPermissionService;
import com.eden.service.SdkMemberService;
import com.eden.web.common.provider.BeforeSecurityDetermineInfoProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

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

    @Autowired
    BeforeSecurityDetermineInfoProvider beforeSecurityDetermineInfoProvider;

    /**
     * 请求之前拦截
     * <p>
     * 1、数据库验证该APPid是否存在
     *</p>
     * <p>
     *   2、判断当前appid企业是否存在该URI路径权限 根据注解权限判断，请查看{@link Access}
     * </p>
     * @param request
     * @param response
     * @param handler
     * @author eden
     * @since 2019/10/29
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] memberInfo = beforeSecurityDetermineInfoProvider.determineHeader(request, response);
        if (Objects.isNull(memberInfo)) return false;
        if (beforeSecurityDetermineInfoProvider.determinePermissionAnnotation(response, handler)) return false;
        if (beforeSecurityDetermineInfoProvider.determineMemberAccess(request, response)) return false;
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //ignore

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        //ignore
    }
}
