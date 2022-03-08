package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.StudentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<StudentDO> getStudents();
}
