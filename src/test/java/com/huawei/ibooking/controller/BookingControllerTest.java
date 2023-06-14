package com.huawei.ibooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.huawei.ibooking.BookingApplication;
import com.huawei.ibooking.model.BookingDO;
import com.huawei.ibooking.model.StudentDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class BookingControllerTest {

    private final String url = "/booking";
    private final String attendance_url = "/booking/attendance/{stuNum}"; //签到

    private final String default_url = "/booking/default/{stuNum}"; // 违约记录

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    //签到
    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_has_booking() throws Exception{
        final BookingDO bookingDO = addBooking();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(attendance_url, bookingDO.getStuNum())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //查看所有违约记录
    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_has_default() throws Exception{
        final BookingDO bookingDO = addBooking();
        bookingDO.setStatus(2);
        mockMvc.perform(MockMvcRequestBuilders.
                get(default_url,bookingDO.getStuNum())
                .contentType(MediaType.APPLICATION_JSON) //请求格式json
                .accept(MediaType.APPLICATION_JSON)) //返回格式json
                .andExpect(status().isOk());
    }

//    insert into tbl_booking_status (seatId, startTime, endTime, stuNum,isEnd)
//    values (1,'2023-05-28 9:00', '2023-05-28 11:00', '01010101',0),
    private BookingDO addBooking() throws Exception{
        final BookingDO bookingDO = new BookingDO();
        bookingDO.setSeatId(3);
        bookingDO.setStartTime(LocalDateTime.of(2023, 6, 3, 11, 0));
        bookingDO.setEndTime(LocalDateTime.of(2023, 7, 3, 15, 0));
        bookingDO.setStuNum("01010104");
        bookingDO.setStatus(0);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        final String json = objectMapper.writeValueAsString(bookingDO);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        return bookingDO;
    }
}
