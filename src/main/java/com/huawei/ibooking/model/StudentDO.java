package com.huawei.ibooking.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 1. @NoArgsConstructor - this annotation is to generate a constructor without parameters
// 2. @Getter and @Setter - these two annotations are to generate getter and setter methods
@NoArgsConstructor
@Getter
@Setter
public class StudentDO {
    //学号
    private String stuNum;
    //姓名
    private String name;
    //登录密码
    private String password;
	//是否为管理员
	private String isAdmin;
	//学生邮件
    private String email;
    //预约记录 用方法实现 不作为属性存在
    //private List<ReservationDo> ReservationList ;
}