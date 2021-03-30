package com.winsel.service;

import com.winsel.dao.*;
import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.TaskType;
import com.winsel.dao.entity.User;
import com.winsel.dao.entity.WeatherTask;
import com.winsel.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class TaskService {
    @Value("${app.apiLink}")
    private String apiLink;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private WeatherTaskRepository weatherTaskRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public WeatherResponse weatherApi(LocalDateTime localDateTime){
        URI targetUrl= UriComponentsBuilder.fromUriString(apiLink)
                .path("/weather")
                .queryParam("localDateTime", localDateTime)
                .build()
                .encode()
                .toUri();
        return restTemplate.getForObject(targetUrl, WeatherResponse.class);
    }

    public Task addNewTask (User userId, LocalDateTime start, LocalTime duration, String taskTypeName, String description) {
        WeatherResponse weather = weatherApi(start);
        WeatherTask weatherTask = weatherTaskRepository.findByWeather(weather.getWeatherMain().get(0).getMain());
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
