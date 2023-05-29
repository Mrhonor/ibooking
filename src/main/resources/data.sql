insert into tbl_student (stuNum, name, password,isAdmin)
values ('01010101', '01010101', 'test123',0),
       ('01010102', '01010102', 'test123',0),
       ('01010103', '01010103', 'test123',0),
       ('01010104', '01010104', 'test123',0),
       ('01010105', '01010105', 'test123',0);


insert into tbl_study_room (stuRoomNumber, buildingNumber, classRoomNumber, openStatus,openingTime, endingTime)
values ('101', 'test101', 'test123', 0,'2021-05-23 08:00', '2024-05-23 23:00'),
       ('102', 'test102', 'test123', 1,'2021-05-23 08:30', '2024-05-23 09:30'),
       ('103', 'test103', 'test123', 0,'2021-05-23 10:00', '2024-05-23 23:00'),
       ('104', 'test104', 'test123', 0,'2021-05-23 10:00', '2024-05-23 23:00'),
       ('105', 'test105', 'test123', 0,'2021-05-23 10:00', '2024-05-23 23:00');

insert into tbl_seat (seatNum, studyRoomId,isVacant)
values (1,1,1),(2,1,1),(1,2,1);

insert into tbl_booking_status (seatId, startTime, endTime, stuNum,isEnd)
values (1,'2023-05-28 9:00', '2023-05-28 11:00', '01010101',0),
        (1,'2023-05-28 15:00', '2023-05-28 20:00', '01010101',1),
        (2,'2023-05-28 10:00', '2023-05-28 22:00', '01010102',0);