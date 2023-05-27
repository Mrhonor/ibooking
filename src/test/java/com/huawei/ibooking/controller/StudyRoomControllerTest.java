package com.huawei.ibooking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.ibooking.BookingApplication;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class StudyRoomControllerTest {
    private final String url = "/studyroom";
    private final String queryStuByNumUrl = "/studyroom/{stuRoomNumber}";

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
    public void should_be_success_when_add_a_new_StudyRoom() throws Exception {
        final StudyRoomDO stuRoomDo = addNewStudyRoom();
        final StudyRoomDO queryDo = queryStudyRoom(stuRoomDo);

        Assert.assertEquals(stuRoomDo.getStuRoomNumber(), queryDo.getStuRoomNumber());
        Assert.assertEquals(stuRoomDo.getBuildingNumber(), queryDo.getBuildingNumber());
        Assert.assertEquals(stuRoomDo.getClassRoomNumber(), queryDo.getClassRoomNumber());
    }

    // @Test
    // public void should_be_fail_when_adding_the_same_StudyRoom() {
    //     // to be continue
    // }

    @Test
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
    }

    // @Test
    // public void should_be_fail_when_modifying_non_existing_StudyRoom() {
    //     // to be continue
    // }

    @Test
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

    // @Test
    // public void should_be_success_when_delete_non_existing_StudyRoom() {
    //     // to be continue
    // }

    private StudyRoomDO addNewStudyRoom() throws Exception {
        final StudyRoomDO stuRoomDo = new StudyRoomDO();
        stuRoomDo.setStuRoomNumber("test001");
        stuRoomDo.setBuildingNumber("test101");
        stuRoomDo.setClassRoomNumber("test202");
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
}