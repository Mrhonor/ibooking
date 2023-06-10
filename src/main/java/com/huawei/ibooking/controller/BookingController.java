package com.huawei.ibooking.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huawei.ibooking.business.BookingBusiness;
import com.huawei.ibooking.model.BookingDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
public class BookingController {
    @Autowired
    private BookingBusiness bookBiz;

    @GetMapping(value="/booking")
    public ResponseEntity<List<BookingDO>> list() {
        final List<BookingDO> bookings = bookBiz.getAllBooking();
        
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    @GetMapping(value="/booking/student/{stuNum}")
    public ResponseEntity<List<BookingDO>> query(@PathVariable("stuNum") String stuNum) {
        Optional <List<BookingDO>> bookings = bookBiz.getStuBooking(stuNum);
        
        return bookings.map(bookingDO-> new ResponseEntity<>(bookingDO, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @GetMapping(value="/booking/seat/{seatId}")
    public ResponseEntity<List<BookingDO>> query(@PathVariable("seatId") int seatId) {
        Optional <List<BookingDO>> bookings = bookBiz.getSeatBooking(seatId);
        
        return bookings.map(bookingDO-> new ResponseEntity<>(bookingDO, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PutMapping(value = "/booking")
    public ResponseEntity<Void> save(@RequestBody BookingDO bk) {
        boolean result = bookBiz.updateBooking(bk);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/booking")
    public ResponseEntity<Void> add(@RequestBody BookingDO bk) {
        boolean result = bookBiz.saveBooking(bk);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/booking", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@RequestBody BookingDO bk) {
        boolean result = bookBiz.deleteBooking(bk.getId());
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/booking/attendance/{stuNum}")
    @ResponseStatus(HttpStatus.OK)
    public boolean SignIn(@PathVariable("stuNum") String stuNum){
        final BookingDO signin = bookBiz.get_sign_in(stuNum);
        LocalDateTime now = LocalDateTime.now();
        //当前时间晚于签退时间就把状态改为2
        if (now.compareTo(signin.getEndTime())>0){
            bookBiz.sign_in_success(stuNum,2);
            return false;
        }
        else{
            return bookBiz.sign_in_success(stuNum,1);
        }
    }


}
