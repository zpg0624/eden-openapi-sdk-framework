package com.eden.core.provider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class SecurityDetermineInfoProvider {

    public abstract void determineHeader(HttpServletRequest request, HttpServletResponse response);

    public abstract void determinePermissionAnnotation(HttpServletResponse response, Object handler);

    public abstract void determineMemberAccess(HttpServletRequest request, HttpServletResponse response);

    public void determineSecurityInfoProcess(HttpServletRequest request, HttpServletResponse response, Object handler) {
        determineHeader(request, response);
        determinePermissionAnnotation(response, handler);
        determineMemberAccess(request, response);
    }
}
