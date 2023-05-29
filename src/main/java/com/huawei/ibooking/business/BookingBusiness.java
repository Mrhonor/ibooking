package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.BookingDao;
import com.huawei.ibooking.model.BookingDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookingBusiness {
    @Autowired
    private BookingDao bookingDao;

    public List<BookingDO> getAllBooking(){
        return bookingDao.getAllBooking();
    }

    public Optional <List<BookingDO>> getStuBooking(final String stuNum) {
        List<BookingDO> bookings = bookingDao.getStuBooking(stuNum);
        if (bookings.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(bookings);
    }
    
    public Optional <List<BookingDO>> getSeatBooking(final int seatId) {
        List<BookingDO> bookings = bookingDao.getSeatBooking(seatId);
        if (bookings.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(bookings);
    }

    public boolean saveBooking(final BookingDO bk) {
        return bookingDao.saveBooking(bk);
    }
    public boolean updateBooking(final BookingDO bk) {
        return bookingDao.updateBooking(bk);
    }
    public boolean setStatus(final int bookingId, final int isTimeout, final int isEnd) {
        return bookingDao.setStatus(bookingId, isTimeout, isEnd);
    }


    public boolean deleteBooking(final int bookingId) {
        return bookingDao.deleteBooking(bookingId);
    }
}
