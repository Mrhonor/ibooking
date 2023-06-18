package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.SeatDao;
import com.huawei.ibooking.model.SeatDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeatBusiness {
    @Autowired
    private SeatDao seatDao;

    public List<SeatDO> getSeats() {
        return seatDao.getSeats();
    }

    public List<SeatDO> getSeat(final String id) {
        List<SeatDO> seats = seatDao.getSeat(id);
        return seats;
    }

    public SeatDO getSeatByseatNumAndstudyRoomID(final int seatNum, final String studyRoomId) {
        List<SeatDO> seats = seatDao.getSeatByseatNumAndstudyRoomID(seatNum, studyRoomId);
        if (seats.size() == 0) {
            return null;
        }
        else {
            return seats.get(0);
        }

    }

    public boolean saveSeat(final SeatDO seat) {
        return seatDao.saveSeat(seat);
    }
    public boolean deleteSeat(final int id) {
        return seatDao.deleteSeat(id);
    }
}
