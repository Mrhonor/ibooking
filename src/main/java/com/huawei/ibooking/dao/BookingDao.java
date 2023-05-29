package com.huawei.ibooking.dao;

import java.util.List;

import com.huawei.ibooking.mapper.BookingMapper;
import com.huawei.ibooking.model.BookingDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingDao {
    @Autowired
    private BookingMapper bookingMapper;

    public List<BookingDO> getAllBooking(){
        return bookingMapper.getAllBooking();
    }

    public List<BookingDO> getStuBooking(final String stuNum) {
        return bookingMapper.getStuBooking(stuNum);
    }
    public List<BookingDO> getSeatBooking(final int seatId) {
        return bookingMapper.getSeatBooking(seatId);
    }

    public boolean saveBooking(final BookingDO bk) {
        return bookingMapper.saveBooking(bk) > 0;
    }
    public boolean setStatus(final int bookingId, final int isTimeout, final int isEnd) {
        return bookingMapper.setStatus(bookingId, isTimeout, isEnd) > 0;
    }
    public boolean updateBooking(final BookingDO bk) {
        return bookingMapper.updateBooking(bk) > 0;
    }

    public boolean deleteBooking(final int bookingId) {
        return bookingMapper.deleteBooking(bookingId) > 0;
    }


}
