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
//座位信息
public class SeatDo {
    private int seatNumber; // 座位编号
    private LocalDateTime start; // 座位可用起始时间
    private LocalDateTime end; // 座位可用截止时间
    private boolean hasOutlet; // 是否有插座
    private boolean isVacant; // 是否空闲

}
