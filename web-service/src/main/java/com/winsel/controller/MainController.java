package com.winsel.controller;

import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.TaskType;
import com.winsel.dao.entity.User;
import com.winsel.dao.entity.WeatherTask;
import com.winsel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.*;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class MainController {
    @Autowired
    private TaskService taskService;

    public ResponseEntity<String> addNewTask (@RequestParam User userId, @RequestParam LocalDateTime start, @RequestParam LocalTime duration, @RequestParam TaskType taskTypeId, @RequestParam String description, @RequestParam WeatherTask weatherTaskId) {
        Task t = taskService.addNewTask(userId,start,duration,taskTypeId,description,weatherTaskId);
        URI uri = URI.create(taskService.getTaskById(t.getId()).toString());
        return ResponseEntity.created(uri).build();
    }
    @GetMapping("/")
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById (@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.getTaskById(id),HttpStatus.OK);
    }
    @GetMapping("/f")
    public ResponseEntity<Iterable<Task>> getFilterTasks(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                         @RequestParam(required = false) Optional<String> type) {
        return new ResponseEntity<>(taskService.getFilterTasks(from,to,type),HttpStatus.OK);
    }
}
