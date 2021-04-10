package com.winsel.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "weather_tasks")
@Getter @Setter
public class WeatherTask {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String weather;
}
