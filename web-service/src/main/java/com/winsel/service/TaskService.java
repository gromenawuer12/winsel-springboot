package com.winsel.service;

import com.winsel.dao.*;
import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.TaskType;
import com.winsel.dao.entity.User;
import com.winsel.dao.entity.WeatherTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task addNewTask (User userId, LocalDateTime start, LocalTime duration, TaskType taskTypeId, String description, WeatherTask weatherTaskId) {
        Task t = new Task();
        t.setUserId(userId);
        t.setStart(start);
        t.setDuration(duration);
        t.setTaskTypeId(taskTypeId);
        t.setDescription(description);
        t.setWeatherTaskId(weatherTaskId);
        t = taskRepository.save(t);
        return t;
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById (Integer id) {
        return taskRepository.findById(id);
    }

    public Iterable<Task> getFilterTasks(LocalDate from, LocalDate to, Optional<String> type) {
        String taskType = type.orElse(null);
        Iterable<Task> tr = getAllTasks();
        List<Task> tasks = new ArrayList<>();
        tr.forEach((task) -> {
            if ((task.getStart().toLocalDate().equals(from)||task.getStart().toLocalDate().isAfter(from)) &&
                    (task.getStart().toLocalDate().equals(to)||task.getStart().toLocalDate().isBefore(to))) {
                if(taskType!=null){
                    if (task.getTaskTypeId().getName().equals(taskType)) tasks.add(task);
                }
                else {
                    tasks.add(task);
                }
            }
        });
        return tasks;
    }
}
