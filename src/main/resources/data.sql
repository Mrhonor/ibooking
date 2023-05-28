insert into student (stuNum, name, password, isAdmin, email)
values ('01010101', '01010101', 'test123', 0, 'edfsa@qdsa.com'),
       ('01010102', '01010102', 'test123', 0, 'edfsa@qdsa.com'),
       ('01010103', '01010103', 'test123', 0, 'edfsa@qdsa.com'),
       ('01010104', '01010104', 'test123', 0, 'edfsa@qdsa.com'),
       ('01010105', '01010105', 'test123', 0, 'edfsa@qdsa.com');

insert into tbl_study_room (stuRoomNumber, buildingNumber, classRoomNumber, openStatus)
values ('101', 'test101', 'test123', 0),
       ('102', 'test102', 'test123', 0),
       ('103', 'test103', 'test123', 0),
       ('104', 'test104', 'test123', 0),
       ('105', 'test105', 'test123', 0);

insert into tbl_seat (seatNum, studyRoomId,isVacant)
values (1,1,1),(2,1,1),(1,2,1);