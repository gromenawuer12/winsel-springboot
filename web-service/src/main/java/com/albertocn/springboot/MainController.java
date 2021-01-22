package com.albertocn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/tasks")
public class MainController {
    @Autowired
    private TasksRepository tasksRepository;

    @PostMapping(path="/add")
    public ResponseEntity<String> addNewTask (@RequestParam Users user_id, @RequestParam LocalDateTime start, @RequestParam LocalTime duration, @RequestParam Task_Types task_type_id, @RequestParam String description, @RequestParam Weather_Tasks weather_task_id) {

        Tasks t = new Tasks();
        t.setUser_id(user_id);
        t.setStart(start);
        t.setDuration(duration);
        t.setTask_type_id(task_type_id);
        t.setDescription(description);
        t.setWeather_task_id(weather_task_id);
        tasksRepository.save(t);
        return new ResponseEntity<>("Saved!", HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public ResponseEntity<Iterable<Tasks>> getAllTasks() {
        return new ResponseEntity<>(tasksRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping(path="/monthlyTasks")
    public ResponseEntity<Iterable<Tasks>>getMonthlyTasks(@RequestParam(value = "date") YearMonth date) {
        Iterable<Tasks> tr = getAllTasks().getBody();
        List<Tasks> tasks = new ArrayList<>();
        tr.forEach((task) -> {
            if (task.getStart().getMonth() == date.getMonth() &&
                    task.getStart().getYear() == date.getYear()) {
                tasks.add(task);
            }
        });
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }

    @GetMapping(path="/dailyTasks")
    public ResponseEntity<Iterable<Tasks>> getDailyTasks(@RequestParam(value = "localDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        Iterable<Tasks> tr = getAllTasks().getBody();
        List<Tasks> tasks = new ArrayList<>();
        tr.forEach((task) -> {
            if (task.getStart().toLocalDate().equals(localDate)) {
                tasks.add(task);
            }
        });
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }
}
