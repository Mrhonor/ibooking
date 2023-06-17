package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.BookingDao;
import com.huawei.ibooking.model.BookingDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.awt.print.Book;
import java.time.LocalDateTime;
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

    public List<BookingDO> get_default_record(final String stuNum) {
        return bookingDao.get_default_record(stuNum);
    }

    public BookingDO get_sign_in(final String stuNum){
        return bookingDao.get_sign_in(stuNum);
    }

    public BookingDO get_sign_out(final String stuNum){
        return bookingDao.get_sign_out(stuNum);
    }

    public boolean sign_in_success(final String stuNum,final int status){
        return bookingDao.sign_in_success(stuNum,status);
    }

    public List<BookingDO> getAllDefaultBooking(final String stuNum){
        return bookingDao.get_default_record(stuNum);
    }

    public List<BookingDO> not_check_in(){return bookingDao.not_check_in();}

    public List<BookingDO> sign_out(){return bookingDao.sign_out();}

}
