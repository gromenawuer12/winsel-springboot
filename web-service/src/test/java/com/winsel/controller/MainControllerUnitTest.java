package com.winsel.controller;

import com.winsel.dao.entity.TaskType;
import com.winsel.dao.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.winsel.dao.entity.Task;
import com.winsel.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MainControllerUnitTest {

    @InjectMocks
    private MainController mainController;

    @Mock
    private TaskService taskService;

    @Test
    void addNewTaskTest(){
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

        when(taskService.addNewTask(t.getUserId(),t.getStart(),t.getDuration(),t.getTaskTypeId().getName(),t.getDescription())).thenReturn(t);

        assertEquals(ResponseEntity.created(URI.create(taskService.getTaskById(t.getId()).toString().replaceAll("(Optional\\[)|(\\])",""))).build(),mainController.addNewTask(t.getUserId(),t.getStart(),t.getDuration(),t.getTaskTypeId().getName(),t.getDescription()));
    }

    @Test
    void getAllTasksTest(){
        assertEquals(new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK),mainController.getFilterTasks(null,null,null));
    }


    @Test
    void getTaskByIdTest(){
        assertEquals(new ResponseEntity<>(taskService.getTaskById(0),HttpStatus.OK),mainController.getTaskById(0));
    }
}