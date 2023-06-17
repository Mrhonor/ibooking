package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.StudentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<StudentDO> getStudents();

    List<StudentDO> getStudent(@Param("stuNum") String stuNum);

    int saveStudent(@Param("stu") StudentDO stu);

    int deleteStudent(@Param("stuNum") String stuNum);
    
    StudentDO login(@Param("stuNum") String stuNum,@Param("password") String password);
    int register(@Param("stuNum") String stuNum,@Param("password") String password,@Param("name") String name,@Param("isAdmin") String isAdmin,@Param("email") String email);

    String getEmail(@Param("stuNum") String stuNum);

}
