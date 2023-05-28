package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.SeatDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeatMapper {
    List<SeatDO> getSeats();

    List<SeatDO> getSeat(@Param("id") int id);

    int saveSeat(@Param("seat") SeatDO seat);

    int deleteSeat(@Param("id") int id);
    
}
