package com.huawei.ibooking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// 1. @NoArgsConstructor - this annotation is to generate a constructor without parameters
// 2. @Getter and @Setter - these two annotations are to generate getter and setter methods
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//学生表
public class StudentDO{
    private String id;
    //学号
    private String stuNum;
    //姓名
    private String name;
    //登录密码
    private String password;
	//是否为管理员
	private boolean isAdmin;
	//学生邮件
    private String email;

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //     List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
    //     authorities.add(new SimpleGrantedAuthority("Admin"));
    //     authorities.add(new SimpleGrantedAuthority("Student")); 
    //     return authorities;
    // }
    
    // //是否 没过期？
    // @Override
    // public boolean isAccountNonExpired() {
    //     return true;
    // }
    
    // //是否 没锁定？
    // @Override
    // public boolean isAccountNonLocked() {
    //     return true;
    // }
    
    
    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return true;
    // }
    
    // //是否 可用？
    // @Override
    // public boolean isEnabled(){
    //     return true;
    // }

    // @Override
    // public String getUsername() {
    //     return stuNum;
    // }

}
