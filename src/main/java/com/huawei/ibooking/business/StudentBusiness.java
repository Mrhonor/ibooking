package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.StudentDao;
import com.huawei.ibooking.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentBusiness {
    @Autowired
    private StudentDao studentDao;

    public List<StudentDO> getStudents() {
        return studentDao.getStudents();
    }

    public Optional<StudentDO> getStudent(final String stuNum) {
        List<StudentDO> students = studentDao.getStudent(stuNum);
        if (students.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(students.get(0));
    }

    public boolean saveStudent(final StudentDO stu) {
        return studentDao.saveStudent(stu);
    }

    public boolean deleteStudent(final String stuNum) {
        return studentDao.deleteStudent(stuNum);
    }
    public StudentDO login(String stuNum, String password){
        StudentDO students = studentDao.login(stuNum,password);
        return students;

    }
    public boolean register(String stuNum, String password,String name,String isAdmin,String email){
        return studentDao.register(stuNum,password,name,isAdmin,email);
    }



}
