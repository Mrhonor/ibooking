package com.huawei.ibooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//座位信息
public class SeatDO {
    private int seatNumber; // 座位编号
    private boolean hasOutlet; // 是否有插座
    private boolean isVacant; // 是否空闲

}