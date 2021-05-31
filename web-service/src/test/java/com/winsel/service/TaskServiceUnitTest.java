package com.winsel.service;

import com.winsel.dao.TaskRepository;
import com.winsel.dao.TaskTypeRepository;
import com.winsel.dao.WeatherTaskRepository;
import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.TaskType;
import com.winsel.dao.entity.User;
import com.winsel.dao.entity.WeatherTask;
import com.winsel.dto.WeatherResponse;
import com.winsel.dto.WeatherWeather;
import com.winsel.feign.JSONWeather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceUnitTest {

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    JSONWeather jsonWeather;

    @Mock
    WeatherTaskRepository weatherTaskRepository;

    @Mock
    TaskTypeRepository taskTypeRepository;

    @Test
    void weatherApiTest() {
        assertEquals(jsonWeather.getWeather(LocalDateTime.now().toString()),taskService.weatherApi(LocalDateTime.now().toString()));
    }

    @Test
    void addNewTaskTest() {
        LocalDateTime date = LocalDateTime.now();

        WeatherWeather ww = new WeatherWeather();
        ww.setMain("Cloud");

        WeatherResponse wr = new WeatherResponse();
        wr.setDt(123);
        ArrayList<WeatherWeather> wwl = new ArrayList();
        wwl.add(ww);
        wr.setWeatherMain(wwl);

        WeatherTask wt = new WeatherTask();
        wt.setId(555);
        wt.setWeather("Cloud");

        User u = new User();
        u.setId(1);

        TaskType tt = new TaskType();
        tt.setId(1);
        tt.setName("work");

        Task t = new Task();
        t.setTaskTypeId(tt);
        t.setUserId(u);
        t.setDescription("FFF");
        t.setDuration(LocalTime.of(1,30,45));
        t.setStart(LocalDateTime.now());

        when(taskService.weatherApi(date.toString())).thenReturn(wr);
        when(weatherTaskRepository.findByWeather(wr.getWeatherWeather().get(0).getMain())).thenReturn(wt);
        when(taskTypeRepository.findByName(t.getTaskTypeId().getName())).thenReturn(tt);
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(t);

        assertEquals(t,taskService.addNewTask(t.getUserId(),t.getStart(),t.getDuration(),t.getTaskTypeId().getName(),t.getDescription()));
    }

    @Test
    void getAllTasksTest() {
        assertEquals(taskRepository.findAll(),taskService.getAllTasks());
    }

    @Test
    void getTaskByIdTest() {
        assertEquals(taskRepository.findById(0),taskService.getTaskById(0));
    }
}