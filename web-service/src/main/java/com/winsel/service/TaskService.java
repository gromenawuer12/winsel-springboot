package com.winsel.service;

import com.winsel.dao.*;
import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.TaskType;
import com.winsel.dao.entity.User;
import com.winsel.dao.entity.WeatherTask;
import com.winsel.dto.WeatherResponse;
import com.winsel.feign.JSONWeather;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private WeatherTaskRepository weatherTaskRepository;
    @Autowired
    private JSONWeather jsonWeather;

    public WeatherResponse weatherApi(String localDateTime){ return jsonWeather.getWeather(localDateTime); }

    public Task addNewTask (User userId, LocalDateTime start, LocalTime duration, String taskTypeName, String description) {
        WeatherResponse weather = weatherApi(start.toString());
        WeatherTask weatherTask = weatherTaskRepository.findByWeather(weather.getWeatherWeather().get(0).getMain());
        TaskType taskType = taskTypeRepository.findByName(taskTypeName);
        Task t = new Task();
        t.setUserId(userId);
        t.setStart(start);
        t.setDuration(duration);
        t.setTaskTypeId(taskType);
        t.setDescription(description);
        t.setWeatherTaskId(weatherTask);
        t = taskRepository.save(t);
        return t;
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById (Integer id) {
        return taskRepository.findById(id);
    }

    public Iterable<Task> getFilterTasks(LocalDate from, LocalDate to, String type) {
        TaskType typeId = taskTypeRepository.findByName(type);

        if (from!=null && to!=null){
            LocalDateTime fromT = from.atStartOfDay();
            LocalDateTime toT = to.atTime(LocalTime.MAX);
            if(type!=null){
                return taskRepository.findByStartBetweenAndTaskTypeId(fromT,toT,typeId);
            }
            else{
                return taskRepository.findByStartBetween(fromT,toT);
            }
        }
        return taskRepository.findByTaskTypeId(typeId);
    }

}
