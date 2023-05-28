package com.huawei.ibooking.controller;

import com.huawei.ibooking.business.StudentBusiness;
import com.huawei.ibooking.model.ReservationDo;
import com.huawei.ibooking.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentBusiness stuBiz;

    @GetMapping(value = "/student")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentDO>> list() {
        final List<StudentDO> students = stuBiz.getStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping(value = "/student/{strNum}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentDO> query(@PathVariable("strNum") String strNum) {
        Optional<StudentDO> stu = stuBiz.getStudent(strNum);
        return stu.map(studentDO -> new ResponseEntity<>(studentDO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping(value = "/student")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> add(@RequestBody StudentDO student) {
        boolean result = stuBiz.saveStudent(student);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/student")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> save(@RequestBody StudentDO student) {
        boolean result = stuBiz.saveStudent(student);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/student/{stuNum}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable("stuNum") String stuNum) {
        boolean result = stuBiz.deleteStudent(stuNum);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/student/reservation/{stuNum}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservationDo> getreservation(@PathVariable("stuNum") String stuNum) {
        Optional<ReservationDo> reservation = stuBiz.getStudentreservation(stuNum);

        return reservation.map(reservationDo -> new ResponseEntity<>(reservationDo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


}
