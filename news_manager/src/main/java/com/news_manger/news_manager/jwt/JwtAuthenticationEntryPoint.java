package com.news_manger.news_manager.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Set CORS headers using more concise modern approach
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Access-Control-Allow-Headers,Content-Type,Authorization,"+
            "Origin,Accept,Referer,User-Agent,Origin,sec-fetch-mode,sec-fetch-site,Accept,CloudFront-Viewer-Country,CloudFront-Is-Tablet-Viewer,"+
            "CloudFront-Forwarded-Proto,X-Forwarded-Proto,User-Agent, Referer,CloudFront-Is-Mobile-Viewer,CloudFront-Is-SmartTV-Viewer,Host,Accept-Encoding,"+
            "Pragma,X-Forwarded-Port,X-Amzn-Trace-Id,Via,Cache-Control,X-Forwarded-For,X-Amz-Cf-Id,Accept-Language,CloudFront-Is-Desktop-Viewer,sec-fetch-dest"
            );
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");

        // Set content type for better client handling
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Write a more informative error message
        response.getWriter().write("""
            {
                "status": 401,
                "error": "Unauthorized",
                "message": "Full authentication is required to access this resource",
                "path": "%s"
            }
            """.formatted(request.getRequestURI()));
    }
}
