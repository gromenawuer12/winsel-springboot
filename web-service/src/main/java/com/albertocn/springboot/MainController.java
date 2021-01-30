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
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private TaskRepository taskRepository;
    public ResponseEntity<String> addNewTask (@RequestParam User userId, @RequestParam LocalDateTime start, @RequestParam LocalTime duration, @RequestParam TaskType taskTypeId, @RequestParam String description, @RequestParam WeatherTask weatherTaskId) {

        Task t = new Task();
        t.setUserId(userId);
        t.setStart(start);
        t.setDuration(duration);
        t.setTaskTypeId(taskTypeId);
        t.setDescription(description);
        t.setWeatherTaskId(weatherTaskId);
        t = taskRepository.save(t);
        URI uri = URI.create(getTaskById(t.getId()).toString());
        return ResponseEntity.created(uri).build();
    }
    @GetMapping("/tasks")
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        return new ResponseEntity<>(taskRepository.findAll(),HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Optional<Task>> getTaskById (@PathVariable Integer id) {
        return new ResponseEntity<>(taskRepository.findById(id),HttpStatus.OK);
    }
    @GetMapping("/tasks/")
    public ResponseEntity<Iterable<Task>> getFilterTasks(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                         @RequestParam(required = false) String type) {
        Iterable<Task> tr = getAllTasks().getBody();
        List<Task> tasks = new ArrayList<>();
        tr.forEach((task) -> {
            if ((task.getStart().toLocalDate().equals(from)||task.getStart().toLocalDate().isAfter(from)) &&
                    (task.getStart().toLocalDate().equals(to)||task.getStart().toLocalDate().isBefore(to))) {
                if(type!=null){
                    if (task.getTaskTypeId().getName().equals(type)) tasks.add(task);
                }
                else {
                    tasks.add(task);
                }
            }
        });
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }
}
