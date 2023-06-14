package com.huawei.ibooking.mapper;


import com.huawei.ibooking.model.ReservationDo;
import com.huawei.ibooking.model.StudentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReservationMapper {

//    @Select("select * from reservation where studentId = #{id}")
//    public List<ReservationDo> find(int id);

    List<ReservationDo> find(@Param("stuNum") String stuNum);

}
