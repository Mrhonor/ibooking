package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.ReservationMapper;
import com.huawei.ibooking.model.ReservationDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationDao {

    @Autowired
    private ReservationMapper reservationMapper;

    //根据学号查询预约记录
    public List<ReservationDo> getreservation(String stuNum){
        List<ReservationDo> list = reservationMapper.find(stuNum);
        System.out.println(list);
        return list;
    }

}
