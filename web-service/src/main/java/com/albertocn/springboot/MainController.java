package com.albertocn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private TasksRepository tasksRepository;
    public ResponseEntity<String> addNewTask (@RequestParam Users user_id, @RequestParam LocalDateTime start, @RequestParam LocalTime duration, @RequestParam Task_Types task_type_id, @RequestParam String description, @RequestParam Weather_Tasks weather_task_id) {

        Tasks t = new Tasks();
        t.setUser_id(user_id);
        t.setStart(start);
        t.setDuration(duration);
        t.setTask_type_id(task_type_id);
        t.setDescription(description);
        t.setWeather_task_id(weather_task_id);
        t = tasksRepository.save(t);
        URI uri = URI.create(getTaskById(t.getId()).toString());
        return ResponseEntity.created(uri).build();
    }
    @GetMapping("/tasks")
    public ResponseEntity<Iterable<Tasks>> getAllTasks() {
        return new ResponseEntity<>(tasksRepository.findAll(),HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getTaskById (@PathVariable Integer id) {
        return new ResponseEntity<>(tasksRepository.findById(id),HttpStatus.OK);
    }
    @GetMapping("/tasks/")
    public ResponseEntity<Iterable<Tasks>> getFilterTasks(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                         @RequestParam(required = false) String type) {
        Iterable<Tasks> tr = getAllTasks().getBody();
        List<Tasks> tasks = new ArrayList<>();
        tr.forEach((task) -> {
            if ((task.getStart().toLocalDate().equals(from)||task.getStart().toLocalDate().isAfter(from)) &&
                    (task.getStart().toLocalDate().equals(to)||task.getStart().toLocalDate().isBefore(to))) {
                if(type!=null){
                    if (task.getTask_type_id().getName().equals(type)) tasks.add(task);
                }
                else {
                    tasks.add(task);
                }
            }
        });
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }
}
