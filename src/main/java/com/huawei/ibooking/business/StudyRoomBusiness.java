package com.huawei.ibooking.business;


import com.huawei.ibooking.model.StudyRoomDO;
import com.huawei.ibooking.dao.StudyRoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudyRoomBusiness {
    @Autowired
    private StudyRoomDao StudyRoomDao;

    public List<StudyRoomDO> getStudyRooms() {
        return StudyRoomDao.getStudyRooms();
    }

    public Optional<StudyRoomDO> getStudyRoom(final String stuRoomNumber) {
        List<StudyRoomDO> StudyRooms = StudyRoomDao.getStudyRoom(stuRoomNumber);
        if (StudyRooms.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(StudyRooms.get(0));
    }

    public boolean saveStudyRoom(final StudyRoomDO stuRoom) {
        return StudyRoomDao.saveStudyRoom(stuRoom);
    }

    public boolean deleteStudyRoom(final String stuRoomNumber) {
        return StudyRoomDao.deleteStudyRoom(stuRoomNumber);
    }

    public List<StudyRoomDO> getOpenStudyRooms(){
        List<StudyRoomDO> allStudyRooms = getStudyRooms();
        List<StudyRoomDO> openStudyRooms = allStudyRooms.stream().filter(studyRoomDO -> studyRoomDO.getIsOpen() == boolean.True).collect(java.util.stream.Collectors.toList());
        return openStudyRooms;
    }

    public List<StudyRoomDO> getIdleStudyRooms() {
        List<StudyRoomDO> openStudyRooms = getOpenStudyRooms();
        List<StudyRoomDO> idleStudyRooms = openStudyRooms.stream().filter(studyRoomDO -> studyRoomDO.getIsIdle() == 1).collect(java.util.stream.Collectors.toList());
        return idleStudyRooms;
    }
}
