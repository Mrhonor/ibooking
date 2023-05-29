package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.SeatMapper;
import com.huawei.ibooking.model.SeatDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeatDao {
    @Autowired
    private SeatMapper seatMapper;

    public List<SeatDO> getSeats() {
        return seatMapper.getSeats();
    }

    public List<SeatDO> getSeat(final int id) {
        return seatMapper.getSeat(id);
    }
    public boolean saveSeat(final SeatDO seat) {
        return seatMapper.saveSeat(seat) > 0;
    }
    public boolean deleteSeat(final int id) {
        return seatMapper.deleteSeat(id) > 0;
    }
}
