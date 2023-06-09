package com.huawei.ibooking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huawei.ibooking.BookingApplication;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudyRoomDO;
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

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class, properties = "test.mode=true")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class StudyRoomControllerTest {
    private final String url = "/studyroom";
    private final String queryStuByNumUrl = "/studyroom/{stuRoomNumber}";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    ObjectMapper objectMapper;
    

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shoule_be_success_when_query_all_StudyRooms() throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final List<StudyRoomDO> StudyRooms = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<StudyRoomDO>>() {
                });

        Assert.assertEquals(StudyRooms.size(), 5);
    }

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_add_a_new_StudyRoom() throws Exception {
        final StudyRoomDO stuRoomDo = addNewStudyRoom();
        final StudyRoomDO queryDo = queryStudyRoom(stuRoomDo);

        Assert.assertEquals(stuRoomDo.getStuRoomNumber(), queryDo.getStuRoomNumber());
        Assert.assertEquals(stuRoomDo.getBuildingNumber(), queryDo.getBuildingNumber());
        Assert.assertEquals(stuRoomDo.getClassRoomNumber(), queryDo.getClassRoomNumber());
        Assert.assertEquals(stuRoomDo.isOpenStatus(), queryDo.isOpenStatus());
        Assert.assertEquals(stuRoomDo.getStartTime(), queryDo.getStartTime());
        Assert.assertEquals(stuRoomDo.getEndTime(), queryDo.getEndTime());
    }

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_get_open_studyRooms() throws Exception {
        final StudyRoomDO stuRoomDo1 = addNewStudyRoom("test_1","2","3",true, LocalTime.of(8,0),LocalTime.of(22,0));
        final StudyRoomDO stuRoomDo2 = addNewStudyRoom("test_2","2","3",true, LocalTime.of(18,0),LocalTime.of(22,0));
        final StudyRoomDO stuRoomDo3 = addNewStudyRoom("test_3","2","3",true, LocalTime.of(8,0),LocalTime.of(10,0));
        final StudyRoomDO stuRoomDo4 = addNewStudyRoom("test_4","2","3",true, LocalTime.of(8,0),LocalTime.of(20,0));
        
        List<StudyRoomDO> stuRoomDoList = queryOpenningStudyRoom().stream().filter(studyRoomDO -> studyRoomDO.getStuRoomNumber().startsWith("test")).collect(java.util.stream.Collectors.toList());
        // Assert.assertEquals(stuRoomDoList.size(), 2);
        stuRoomDoList = stuRoomDoList.stream().filter(studyRoomDO -> studyRoomDO.getStuRoomNumber().equals("test_1") || studyRoomDO.getStuRoomNumber().equals("test_4")).collect(java.util.stream.Collectors.toList());
        Assert.assertEquals(stuRoomDoList.size(), 2);

    }

    // @Test
    // public void should_be_fail_when_adding_the_same_StudyRoom() {
    //     // to be continue
    // }

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_modifying_existing_StudyRoom() throws Exception {
        final StudyRoomDO stuRoomDo = addNewStudyRoom();
        stuRoomDo.setBuildingNumber("modify123");

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(stuRoomDo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        final StudyRoomDO queryDo = queryStudyRoom(stuRoomDo);

        Assert.assertEquals(stuRoomDo.getStuRoomNumber(), queryDo.getStuRoomNumber());
        Assert.assertEquals(stuRoomDo.getBuildingNumber(), queryDo.getBuildingNumber());
        Assert.assertEquals(stuRoomDo.getClassRoomNumber(), queryDo.getClassRoomNumber());
        Assert.assertEquals(stuRoomDo.isOpenStatus(), queryDo.isOpenStatus());
        Assert.assertEquals(stuRoomDo.getStartTime(), queryDo.getStartTime());
        Assert.assertEquals(stuRoomDo.getEndTime(), queryDo.getEndTime());

    }

    // @Test
    // public void should_be_fail_when_modifying_non_existing_StudyRoom() {
    //     // to be continue
    // }

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_delete_existing_StudyRoom() throws Exception {
        final StudyRoomDO stuRoomDo = addNewStudyRoom();
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "//" + stuRoomDo.getStuRoomNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(queryStuByNumUrl, stuRoomDo.getStuRoomNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional  // 开启事务
    @Rollback  // 测试方法完成后回滚事务
    public void should_be_success_when_query_empty_StudyRoom() throws Exception {
        final StudyRoomDO stuRoomDo1 = addNewStudyRoom("test_r11","2","3",true, LocalTime.of(8,0),LocalTime.of(22,0));
        final StudyRoomDO stuRoomDo2 = addNewStudyRoom("test_h2","2","3",false, LocalTime.of(8,0),LocalTime.of(22,0));

        final SeatDO seat1 = addNewSeat(9990, stuRoomDo1.getId(), 1, 1);
        final SeatDO seat2 = addNewSeat(9991, stuRoomDo1.getId(), 1, 0);
        final SeatDO seat3 = addNewSeat(9992, stuRoomDo2.getId(), 1, 1);

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "/empty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final List<StudyRoomDO> StudyRooms = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<StudyRoomDO>>() {
                });

        
        Assert.assertEquals(StudyRooms.stream().anyMatch(room -> room.getStuRoomNumber() == "test_r11"), true);
        Assert.assertEquals(StudyRooms.stream().noneMatch(room -> room.getStuRoomNumber() == "test_h2"), true);
        
        
    }
    // @Test
    // public void should_be_success_when_delete_non_existing_StudyRoom() {
    //     // to be continue
    // }

    private StudyRoomDO addNewStudyRoom() throws Exception {
        final StudyRoomDO stuRoomDo = new StudyRoomDO();
        stuRoomDo.setStuRoomNumber("test001");
        stuRoomDo.setBuildingNumber("test101");
        stuRoomDo.setClassRoomNumber("test202");
        stuRoomDo.setOpenStatus(true);
        stuRoomDo.setStartTime(LocalTime.of(8, 0));
        stuRoomDo.setEndTime(LocalTime.of(22, 0));
        final String json = new ObjectMapper().writeValueAsString(stuRoomDo);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        return stuRoomDo;
    }

    private StudyRoomDO addNewStudyRoom(String stuRoomNumber, String buildingNumber, String classRoomNumber, 
                                                boolean openStatus, LocalTime staTime, LocalTime endTime) throws Exception {
        final StudyRoomDO stuRoomDo = new StudyRoomDO();
        stuRoomDo.setStuRoomNumber(stuRoomNumber);
        stuRoomDo.setBuildingNumber(buildingNumber);
        stuRoomDo.setClassRoomNumber(classRoomNumber);
        stuRoomDo.setOpenStatus(openStatus);
        stuRoomDo.setStartTime(staTime);
        stuRoomDo.setEndTime(endTime);
        final String json = new ObjectMapper().writeValueAsString(stuRoomDo);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        return stuRoomDo;
    }


    private StudyRoomDO queryStudyRoom(StudyRoomDO stuRoomDo) throws Exception {

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(queryStuByNumUrl, stuRoomDo.getStuRoomNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        return new ObjectMapper().readValue(
                result.getResponse().getContentAsString(), new TypeReference<StudyRoomDO>() {
                });
    }

    private List<StudyRoomDO> queryOpenningStudyRoom() throws Exception {

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "/opening")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        return new ObjectMapper().readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<StudyRoomDO>>() {
                });
    }
    
    private SeatDO addNewSeat(int seatNum, int studyRoomID, int hasOutlet, int isVacant) throws Exception {
        final SeatDO seatDO = new SeatDO();
        
        seatDO.setSeatNum(seatNum);
        seatDO.setStudyRoomId(studyRoomID);
        seatDO.setHasOutlet(hasOutlet);
        seatDO.setIsVacant(isVacant);

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
}