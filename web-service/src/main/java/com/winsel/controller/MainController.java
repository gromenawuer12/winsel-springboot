package com.winsel.controller;

import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.User;
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

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewTask (@RequestParam User userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam LocalTime duration, @RequestParam String taskTypeName, @RequestParam String description) {
        Task t = taskService.addNewTask(userId,start,duration,taskTypeName,description);
        URI uri = URI.create(taskService.getTaskById(t.getId()).toString().replaceAll("(Optional\\[)|(\\])",""));
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Task>> getFilterTasks(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                         @RequestParam(required = false) String type) {
        if((from!=null && to!=null) || type!=null) {
            return new ResponseEntity<>(taskService.getFilterTasks(from, to, type), HttpStatus.OK);
        }
        return new ResponseEntity<>(taskService.getAllTasks(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById (@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.getTaskById(id),HttpStatus.OK);
    }
}
