package com.huawei.ibooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huawei.ibooking.business.StudyRoomBusiness;
import com.huawei.ibooking.model.StudyRoomDO;

import java.util.List;
import java.util.Optional;

@Controller
public class StudyRoomController {
    @Autowired
    private StudyRoomBusiness stuRoomBiz;

    @GetMapping(value = "/studyroom")
    public ResponseEntity<List<StudyRoomDO>> list() {
        final List<StudyRoomDO> studyRooms = stuRoomBiz.getStudyRooms();

        return new ResponseEntity<>(studyRooms, HttpStatus.OK);
    }

    @GetMapping(value = "/studyroom/opening")
    public ResponseEntity<List<StudyRoomDO>> getOpening() {
        final List<StudyRoomDO> studyRooms = stuRoomBiz.getIdleStudyRooms();

        return new ResponseEntity<>(studyRooms, HttpStatus.OK);
    }

    @GetMapping(value = "/studyroom/{stuRoomNumber}")
    public ResponseEntity<StudyRoomDO> query(@PathVariable("stuRoomNumber") String stuRoomNumber) {
        Optional<StudyRoomDO> stuRoom = stuRoomBiz.getStudyRoom(stuRoomNumber);

        return stuRoom.map(studyRoomDO -> new ResponseEntity<>(studyRoomDO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping(value = "/studyroom")
    public ResponseEntity<Void> add(@RequestBody StudyRoomDO studyRoom) {
        boolean result = stuRoomBiz.saveStudyRoom(studyRoom);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/studyroom")
    public ResponseEntity<Void> save(@RequestBody StudyRoomDO studyRoom) {
        boolean result = stuRoomBiz.saveStudyRoom(studyRoom);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/studyroom/{stuRoomNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("stuRoomNumber") String stuRoomNumber) {
        boolean result = stuRoomBiz.deleteStudyRoom(stuRoomNumber);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/studyroom/empty")
    public ResponseEntity<List<StudyRoomDO>> getEmptyStudyRooms() {
        final List<StudyRoomDO> studyRooms = stuRoomBiz.getEmptyStudyRooms();

        return new ResponseEntity<>(studyRooms, HttpStatus.OK);
    }
}
