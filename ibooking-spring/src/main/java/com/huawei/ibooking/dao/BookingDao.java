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
    public List<BookingDO> getBookingById(final int Id) {
        return bookingMapper.getBookingById(Id);
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

    public List<BookingDO> get_default_record(final String stuNum){
        List<BookingDO> list = bookingMapper.find_default_record(stuNum);
        System.out.println(list);
        return list;
    }

    public BookingDO get_sign_in(String stuNum){
        return bookingMapper.find_sign_in(stuNum);
    }

    public BookingDO get_sign_out(String stuNum){
        return bookingMapper.find_sign_out(stuNum);
    }

    public boolean sign_in_success(String stuNum,int status){
        return bookingMapper.sign_in_success(stuNum,status);
    }

    public List<BookingDO> not_check_in(){return bookingMapper.not_check_in();}

    public List<BookingDO> sign_out(){return bookingMapper.sign_out();}
}
