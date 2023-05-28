package com.huawei.ibooking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDo {
    private int id; // 预定记录编号
    private String location;  // 预约位置 eg.A101
    private String stuNum; // 预约学生学号
    private LocalDateTime start;  // 开始预约时间
    private LocalDateTime end; // 预约结束时间
}
