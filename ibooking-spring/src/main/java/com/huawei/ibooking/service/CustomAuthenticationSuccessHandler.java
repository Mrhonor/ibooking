package com.huawei.ibooking.service;

import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.HttpRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import org.springframework.web.servlet.HttpServletBean;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;



@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        // 构建自定义的JSON成功消息
        String username = authentication.getName(); // 获取用户名
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); // 获取用户权限集合

        // 构建JSON对象
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("username", username);
        
        JSONArray jsonAuthorities = new JSONArray();
        for (GrantedAuthority authority : authorities) {
            jsonAuthorities.add(authority.getAuthority());
        }
        jsonResponse.put("authorities", jsonAuthorities);

        // 将JSON对象写入响应
        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse.toString());
        writer.flush();
        writer.close();
    }
}
