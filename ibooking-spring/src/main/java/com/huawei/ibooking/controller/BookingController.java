package com.huawei.ibooking.controller;


import com.huawei.ibooking.business.SeatBusiness;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.business.BookingBusiness;
import com.huawei.ibooking.model.BookingDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class BookingController {
    @Autowired
    private BookingBusiness bookBiz;

    @Autowired
    private SeatBusiness seatBusiness;

    @GetMapping(value="/booking")
    public ResponseEntity<List<BookingDO>> list() {
        final List<BookingDO> bookings = bookBiz.getAllBooking();
        
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping(value="/booking/{id}")
    public ResponseEntity<BookingDO> queryById(@PathVariable("id") int id) {
        final BookingDO booking = bookBiz.getBookingById(id);
        
        return new ResponseEntity<>(booking, HttpStatus.OK);
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

    @PostMapping ("/booking/attendance/{stuNum}")
    @ResponseStatus(HttpStatus.OK)
    public boolean SignIn(@PathVariable("stuNum") String stuNum){
        final BookingDO signin = bookBiz.get_sign_in(stuNum); //获得预约记录
        System.out.println(signin.getStartTime());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime default_time = signin.getStartTime().plusMinutes(15);//十五分钟内未成功签到，则违约
        //当前时间晚于签退时间就把状态改为2
        if (now.compareTo(default_time)>0){
            bookBiz.sign_in_success(stuNum,2);
            System.out.println("签到时超过签到时间违约");
            List<SeatDO> seatDOS = seatBusiness.getSeats().stream().filter(seatdo -> seatdo.getId() == signin.getSeatId()).collect(Collectors.toList());

            for (SeatDO seatDO : seatDOS){
                seatBusiness.saveSeat(seatDO); //释放座位
            }
            return false;
        }
        else{
            return bookBiz.sign_in_success(stuNum,1);
        }
    }

    @GetMapping("/booking/default/{stuNum}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookingDO>> Default(@PathVariable("stuNum") String stuNum){
        final List<BookingDO> default_list = bookBiz.getAllDefaultBooking(stuNum);
        return new ResponseEntity<>(default_list , HttpStatus.OK);
    }

    @PostMapping("/booking/signout/{stuNum}")
    @ResponseStatus(HttpStatus.OK)
    public boolean SignOut(@PathVariable("stuNum") String stuNum) {
        final BookingDO signout = bookBiz.get_sign_out(stuNum); //获得未签退记录
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime default_time = signout.getStartTime().plusMinutes(10);//十分钟内未成功签退，则违约
        System.out.println("now:"+now);
        System.out.println("default:"+default_time);
        //当前时间晚于签退时间就把状态改为2
        if (now.isAfter(default_time)) {
            bookBiz.sign_in_success(stuNum,2);
            System.out.println("签退时超过签退时间违约");
            List<SeatDO> seatDOS = seatBusiness.getSeats().stream().filter(seatdo -> seatdo.getId() == signout.getSeatId()).collect(Collectors.toList());
            // List<SeatDO> seatDOS = seatBusiness.getSeat(signout.getSeatId());
            for (SeatDO seatDO : seatDOS){
                seatBusiness.saveSeat(seatDO); //释放座位
            }
            return false;
        }
        return bookBiz.sign_in_success(stuNum,4);//签退成功
    }

}
