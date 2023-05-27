package com.huawei.ibooking.model;

import java.sql.Time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StudyRoomDO {
    private int id;
    private String stuRoomNumber;
    private String buildingNumber;
    private String classRoomNumber;
    //是否开放
    private boolean isOpen;
    //开放时间
    private Time openTime;
    //关闭时间
    private Time endTime;
}
