package com.winsel.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tasks")
@Getter @Setter
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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


}
