package com.huawei.ibooking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huawei.ibooking.BookingApplication;
import com.huawei.ibooking.model.SeatDO;
import org.junit.After;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;


import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class SeatControllerTest {
    private final String url = "/seat";
    private final String querySeatByIdUrl = "/seat/{id}";

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

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void shoule_be_success_when_query_all_seats() throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();

        final List<SeatDO> seats = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(
            result.getResponse().getContentAsString(), new TypeReference<List<SeatDO>>() {
            });
        Assert.assertEquals(seats.size(), 58);
    }

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_add_a_new_seat() throws Exception {
        final SeatDO seatDO = addNewSeat();

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final List<SeatDO> seats = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(
        result.getResponse().getContentAsString(), new TypeReference<List<SeatDO>>() {
        });
        Assert.assertEquals(seats.size(), 59);
    }

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_delete_existing_seat() throws Exception {
        final SeatDO seatDO = new SeatDO();
        seatDO.setId(2);
        mockMvc.perform(MockMvcRequestBuilders.delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(seatDO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final List<SeatDO> seats = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(
        result.getResponse().getContentAsString(), new TypeReference<List<SeatDO>>() {
        });
        Assert.assertEquals(seats.size(), 57);
    }


    private SeatDO addNewSeat() throws Exception {
        final SeatDO seatDO = new SeatDO();
        seatDO.setId(3);
        seatDO.setSeatNum(3);
        seatDO.setStudyRoomId("101");
        seatDO.setHasOutlet(1);
        seatDO.setIsVacant(1);

        final String json = new ObjectMapper().writeValueAsString(seatDO);

        // final String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(seatDO);
        System.out.println(json);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        return seatDO;
    }

    private SeatDO[] querySeat(SeatDO seatDO) throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(querySeatByIdUrl, seatDO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(
                result.getResponse().getContentAsString(), new TypeReference<SeatDO[]>() {
                });
    }
}
