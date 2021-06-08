package com.winsel.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winsel.app.SpringApplication;
import com.winsel.dao.TaskRepository;
import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.TaskType;
import com.winsel.dto.WeatherResponse;
import com.winsel.dto.WeatherWeather;
import com.winsel.feign.JSONWeather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = SpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/init.sql"})
public class MainControllerIntegrationTest {

    private static final MySQLContainer mySQLContainer;

    static {
        mySQLContainer = (MySQLContainer)(new MySQLContainer("mysql:8.0")
                .withUsername("root")
                .withPassword("test")
                .withReuse(true));
        mySQLContainer.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TaskRepository taskRepository;

    @MockBean
    private JSONWeather jsonWeather;

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getATaskWithSpecificID_thenVerifyResponse() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Task t = taskRepository.findById(1).get();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        assertEquals(gson.toJson(t), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getAllTasks_thenVerifyResponse() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Iterable<Task> t = taskRepository.findAll();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        assertEquals(gson.toJson(t), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getWorkTasks_thenVerifyResponse() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks").param("type","work"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        TaskType tt = new TaskType();
        tt.setName("work");
        tt.setId(1);
        Iterable<Task> t = taskRepository.findByTaskTypeId(tt);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        assertEquals(gson.toJson(t), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void get2020MarchTasksTasks_thenVerifyResponse() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks").param("from","2020-03-01").param("to","2020-03-31"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Iterable<Task> t = taskRepository.findByStartBetween(LocalDateTime.of(2020,3,1,0,0,0),LocalDateTime.of(2020,3,31,23,59,59));

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        assertEquals(gson.toJson(t), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void get2020MarchLeisureTasksTasks_thenVerifyResponse() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks").param("from","2020-03-01").param("to","2020-03-31").param("type","leisure"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        TaskType tt = new TaskType();
        tt.setName("leisure");
        tt.setId(2);

        Iterable<Task> t = taskRepository.findByStartBetweenAndTaskTypeId(LocalDateTime.of(2020,3,1,0,0,0),LocalDateTime.of(2020,3,31,23,59,59),tt);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        assertEquals(gson.toJson(t), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void addTask_thenVerifyResponse() throws Exception{
        WeatherWeather ww = new WeatherWeather();
        ww.setMain("Cloud");

        WeatherResponse wr = new WeatherResponse();
        wr.setDt(123);
        ArrayList<WeatherWeather> wwl = new ArrayList();
        wwl.add(ww);
        wr.setWeatherMain(wwl);

        when(jsonWeather.getWeather(any(String.class))).thenReturn(wr);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/tasks/add")
                .param("userId","1")
                .param("start",LocalDateTime.now().toString())
                .param("duration",LocalTime.now().toString())
                .param("taskTypeName","work")
                .param("description","test description"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isCreated());

        Task t = taskRepository.findById(874).get();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        assertNotNull(gson.toJson(t));
    }

}
