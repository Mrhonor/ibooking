package com.huawei.ibooking.controller;

import com.huawei.ibooking.business.SeatBusiness;
import com.huawei.ibooking.model.SeatDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeatController {
    @Autowired
    private SeatBusiness seatBiz;
    
    @GetMapping(value = "/seat")
    public ResponseEntity<List<SeatDO>> list() {
        final List<SeatDO> seats = seatBiz.getSeats();

        return new ResponseEntity<>(seats, HttpStatus.OK);
    }
    
    @GetMapping(value = "/seat/{id}")
    public ResponseEntity<List<SeatDO>> query(@PathVariable("id") int id) {
        final List<SeatDO> seats = seatBiz.getSeat(id);

        return new ResponseEntity<>(seats, HttpStatus.OK);
        // Optional<StudyRoomDO> stu = strBiz.getStudyRoom(buildingNumber, classRoomNumber);

        // return stu.map(studyRoomDO -> new ResponseEntity<>(studyRoomDO, HttpStatus.OK))
        //         .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping(value = "/seat")
    public ResponseEntity<Void> add(@RequestBody SeatDO seat) {
        boolean result = seatBiz.saveSeat(seat);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/seat")
    public ResponseEntity<Void> save(@RequestBody SeatDO seat) {
        boolean result = seatBiz.saveSeat(seat);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/seat")
    public ResponseEntity<Void> delete(@RequestBody SeatDO seat) {
        boolean result = seatBiz.deleteSeat(seat.getId());
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
