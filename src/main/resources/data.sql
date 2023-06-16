insert into student (stuNum, name, password, isAdmin, email)
values ('01010101', '01010101', 'test123', 1, 'edfsa@qdsa.com'),
       ('01010102', '01010102', 'test123', 0, 'edfsa@qdsa.com'),
       ('01010103', '01010103', 'test123', 0, 'edfsa@qdsa.com'),
       ('01010104', '01010104', 'test123', 0, 'edfsa@qdsa.com'),
       ('01010105', '01010105', 'test123', 0, 'edfsa@qdsa.com');


insert into tbl_study_room (stuRoomNumber, buildingNumber, classRoomNumber, openStatus,startTime, endTime)
values ('101', 'test101', 'test123', 0,'08:00', '23:00'),
       ('102', 'test102', 'test123', 1,'08:30', '09:30'),
       ('103', 'test103', 'test123', 0,'10:00', '23:00'),
       ('104', 'test104', 'test123', 0,'10:00', '23:00'),
       ('105', 'test105', 'test123', 0,'10:00', '23:00');

insert into tbl_seat (seatNum, studyRoomId,isVacant)
values (1,1,1),(2,1,1),(1,2,1);

insert into tbl_booking_status(seatId, startTime, endTime, stuNum,isEnd,status)
values (1,'2023-05-28 9:00', '2023-06-03 11:00', '01010101',0,1),
        (1,'2023-05-28 15:00', '2023-05-28 20:00', '01010101',1,2),
        (2,'2023-05-28 10:00', '2023-05-28 22:00', '01010102',0,1);
