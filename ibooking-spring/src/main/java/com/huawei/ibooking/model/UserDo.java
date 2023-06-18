package com.huawei.ibooking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDo {
    private String id; // 用户id
    private int permission; // 用户权限
}
