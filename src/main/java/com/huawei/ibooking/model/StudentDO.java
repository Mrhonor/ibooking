package com.huawei.ibooking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// 1. @NoArgsConstructor - this annotation is to generate a constructor without parameters
// 2. @Getter and @Setter - these two annotations are to generate getter and setter methods
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//学生表
public class StudentDO {
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
}
