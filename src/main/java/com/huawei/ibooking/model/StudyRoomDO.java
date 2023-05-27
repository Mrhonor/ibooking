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
//自习室信息
public class StudyRoomDO {
    //自习室编号
    private int id;
    //所处楼栋 eg. A（栋）、B、C
    private String buildingNumber;
    //所处教室 eg.101、102
    private String classRoomNumber;
    //是否开放
    private boolean isopen;
    //开放时间
    private LocalDateTime opentime = LocalDateTime.of(2023, 5, 27, 10, 30, 0);
    //是否有空位
    private boolean is_free_seat;
}
