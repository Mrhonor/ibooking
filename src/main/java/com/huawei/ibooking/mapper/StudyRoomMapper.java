package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.StudyRoomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyRoomMapper {
    List<StudyRoomDO> getStudyRooms();

    List<StudyRoomDO> getStudyRoom(@Param("stuRoomNumber") String stuRoomNumber);

    int saveStudyRoom(@Param("stuRoom") StudyRoomDO stuRoom);

    int deleteStudyRoom(@Param("stuRoomNumber") String stuRoomNumber);
}
