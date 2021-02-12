package com.winsel.dao.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @OneToOne()
    @JoinColumn(name = "userId")
    private User userId;

    private LocalDateTime start;

    private LocalTime duration;

    @OneToOne()
    @JoinColumn(name = "taskTypeId")
    private TaskType taskTypeId;

    private String description;

    @OneToOne()
    @JoinColumn(name = "weatherTaskId")
    private WeatherTask weatherTaskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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

    public TaskType getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(TaskType taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WeatherTask getWeatherTaskId() {
        return weatherTaskId;
    }

    public void setWeatherTaskId(WeatherTask weatherTaskId) {
        this.weatherTaskId = weatherTaskId;
    }

}
