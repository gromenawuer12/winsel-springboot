package com.albertocn.springboot;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private Users user_id;

    private LocalDateTime start;

    private LocalTime duration;

    @OneToOne()
    @JoinColumn(name = "task_type_id")
    private Task_Types task_type_id;

    private String description;

    @OneToOne()
    @JoinColumn(name = "weather_task_id")
    private Weather_Tasks weather_task_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser_id() {
        return user_id;
    }

    public void setUser_id(Users user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Task_Types getTask_type_id() {
        return task_type_id;
    }

    public void setTask_type_id(Task_Types task_type_id) {
        this.task_type_id = task_type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Weather_Tasks getWeather_task_id() {
        return weather_task_id;
    }

    public void setWeather_task_id(Weather_Tasks weather_task_id) {
        this.weather_task_id = weather_task_id;
    }

}
