package com.huawei.ibooking.controller;

import com.huawei.ibooking.model.StudentDO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    @GetMapping(value = "/student")
    public ResponseEntity<List<StudentDO>> list() {
        final List<StudentDO> students = new ArrayList<>();

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
