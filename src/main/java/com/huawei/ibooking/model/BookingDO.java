package com.huawei.ibooking.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BookingDO {
    private int id;
    private int seatId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;
    private boolean isEnd;
    private boolean isTimeout;
    private String stuNum;
    private String name;
    private String buildingNum;
    private String classRoomNum;
    private int status; // 0:未签到 1：签到 2：违约
}
