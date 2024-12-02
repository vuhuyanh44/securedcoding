package com.lgcns.hrm.cv.security;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.domain.Feedback;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.common.utils.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author pigx
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Feedback feedback = response.getStatus() == HttpServletResponse.SC_NOT_FOUND ? ErrorCodes.NOT_FOUND : ErrorCodes.INVALID_TOKEN;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(JsonUtil.toJson(Result.failure(feedback)));
        response.setStatus(feedback.getStatus());
    }
}