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
    private int id;
    private int seatNum;// 座位编号
    private int studyRoomId;
    private int isVacant; // 是否空闲
    private int hasOutlet; // 是否有插座

}