package com.huawei.ibooking.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 构建自定义的JSON错误消息
        String errorMessage = "身份验证失败"; // 自定义错误消息内容
        String jsonErrorMessage = "{\"message\":\"" + errorMessage + "\"}";

        // 将JSON错误消息写入响应
        PrintWriter writer = response.getWriter();
        writer.write(jsonErrorMessage);
        writer.flush();
        writer.close();
    }
}