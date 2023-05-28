drop table if exists tbl_student;
create table tbl_student
(
    id       int         not null auto_increment,
    stuNum   varchar(16) not null,
    name     varchar(16) not null,
    password varchar(16) not null,
    email    varchar(128),
    isAdmin  tinyint(1) not null default 0,
    primary key (id),
    unique (stuNum)
);

drop table if exists tbl_study_room cascade;
create table tbl_study_room
(
    id           int         not null auto_increment,
    stuRoomNumber   varchar(16) not null,
    buildingNumber  varchar(16) not null,
    classRoomNumber varchar(16) not null,
    openStatus BIT(1) not null,
    startTime TIME,
    endTime TIME,
    primary key (id),
    unique (stuRoomNumber)
);

drop table if exists tbl_seat;
create table tbl_seat
(
    id              int not null auto_increment,
    seatNum         int not null,
    studyRoomId     int not null,
    isVacant        tinyint(1) not null default 1,
    hasOutlet       tinyint(1) not null default 0,
    primary key (id)
    -- foreign key (studyRoomId) references tbl_study_room(id) on delete cascade,
    -- unique(seatNum, studyRoomId)
);

drop table if exists tbl_booking_xxx;
create table tbl_booking_xxx
(
    id            int not null auto_increment,
    seatId        int not null,
    bookingPeriod int not null,
    stuId         int not null,
    primary key (id)
);