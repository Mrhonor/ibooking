package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.StudentDao;
import com.huawei.ibooking.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentBusiness {
    @Autowired
    private StudentDao studentDao;

    public List<StudentDO> getStudents() {
        return studentDao.getStudents();
    }
}
