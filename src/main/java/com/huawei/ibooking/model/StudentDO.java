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
    private int id;
    private String stuNum;
    private String name;
    private String password;
    private String isAdmin;
    private String email;
}
