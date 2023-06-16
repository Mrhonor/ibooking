package com.huawei.ibooking.business;


import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudyRoomDO;
import com.huawei.ibooking.dao.SeatDao;
import com.huawei.ibooking.dao.StudyRoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class StudyRoomBusiness {
    @Value("${test.mode:false}")
    private boolean testMode;

    @Autowired
    private StudyRoomDao StudyRoomDao;

    @Autowired
    private SeatDao seatDao;

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
        List<StudyRoomDO> openStudyRooms = allStudyRooms.stream().filter(studyRoomDO -> studyRoomDO.isOpenStatus() == true).collect(java.util.stream.Collectors.toList());
        return openStudyRooms;
    }

    public List<StudyRoomDO> getIdleStudyRooms() {
        List<StudyRoomDO> openStudyRooms = getOpenStudyRooms();
        LocalTime currentTime;
        if (!testMode) {
            currentTime = LocalTime.now();
        }
        else{
            currentTime = LocalTime.of(12, 0);
        }
        
        List<StudyRoomDO> idleStudyRooms = openStudyRooms.stream().filter(studyRoomDO -> currentTime.isAfter(studyRoomDO.getStartTime()) && currentTime.isBefore(studyRoomDO.getEndTime())).collect(java.util.stream.Collectors.toList());
        return idleStudyRooms;
    }

    public List<StudyRoomDO> getEmptyStudyRooms() {
        List<SeatDO> allSeats = seatDao.getSeats();
        List<StudyRoomDO> allStudyRooms = getStudyRooms();
        List<StudyRoomDO> emptyStudyRooms = allStudyRooms.stream()
                            .filter(studyRoomDO -> studyRoomDO.isOpenStatus() == true && allSeats.stream()
                            .filter(seatDO -> seatDO.getIsVacant() == 1 && seatDO.getStudyRoomId() == studyRoomDO.getId())
                            .count() != 0).collect(java.util.stream.Collectors.toList());
        return emptyStudyRooms;
    }
}
