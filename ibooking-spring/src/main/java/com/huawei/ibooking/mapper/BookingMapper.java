package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.BookingDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookingMapper {
    List<BookingDO> getAllBooking();

    List<BookingDO> getStuBooking(@Param("stuNum") String stuNum);
    List<BookingDO> getSeatBooking(@Param("seatId") int seatId);


    int saveBooking(@Param("bk") BookingDO bk);

    int setStatus(@Param("bookingId") int bookingId, @Param("isTimeout") int isTimeout, @Param("isEnd") int isEnd);

    int updateBooking(@Param("bk") BookingDO bk);
    int deleteBooking(@Param("bookingId") int bookingId);

    List<BookingDO> find_default_record(@Param("stuNum") String stuNum);

    BookingDO find_sign_in(@Param("stuNum") String stuNum);

    BookingDO find_sign_out(@Param("stuNum") String stuNum);

    boolean sign_in_success(@Param("stuNum") String stuNum,@Param("status") int status);

    List<BookingDO> not_check_in();

    List<BookingDO> sign_out();

    List<BookingDO> getBookingById(@Param("id") int Id);

}
