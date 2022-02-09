package com.eden.core.provider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityDetermineInfoProvider {

    boolean determineHeader(HttpServletRequest request, HttpServletResponse response);

    boolean determinePermissionAnnotation(HttpServletResponse response, Object handler);

    boolean determineMemberAccess(HttpServletRequest request, HttpServletResponse response);
}
