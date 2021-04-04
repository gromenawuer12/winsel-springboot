package com.winsel.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @OneToOne()
    @JoinColumn(name = "userId")
    @Getter @Setter private User userId;

    @Getter @Setter private LocalDateTime start;

    @Getter @Setter private LocalTime duration;

    @OneToOne()
    @JoinColumn(name = "taskTypeId")
    @Getter @Setter private TaskType taskTypeId;

    @Getter @Setter private String description;

    @OneToOne()
    @JoinColumn(name = "weatherTaskId")
    @Getter @Setter private WeatherTask weatherTaskId;


}
