package com.huawei.ibooking.controller;

import com.huawei.ibooking.business.BookingBusiness;
import com.huawei.ibooking.model.BookingDO;
import com.huawei.ibooking.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Controller
public class ReminderController {

    @Autowired
    private BookingBusiness bookingBusiness;

    @Autowired
    private MailService mailService;

    //发邮件尝试 localhost:8090/sendTextMail?to=22210240301@m.fudan.edu.cn&subject=test&text=test1
    @RequestMapping("/sendTextMail")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> sendTextMail(String to, String subject, String text){
        mailService.sendTextMailMessage(to,subject,text);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/not_check_in")
    public ResponseEntity<List<BookingDO>> not_check_in() {
        Optional<List<BookingDO>> bookings = Optional.ofNullable(bookingBusiness.not_check_in());
        return bookings.map(bookingDO-> new ResponseEntity<>(bookingDO, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }



//    @RequestMapping("/Reminder")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Void> reminder(){
//        mailService.sendReminder();
//        return ResponseEntity.ok().build();
//    }

}
