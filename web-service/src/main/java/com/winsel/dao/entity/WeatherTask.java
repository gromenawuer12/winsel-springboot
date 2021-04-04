package com.winsel.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "weather_tasks")
public class WeatherTask {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter private Integer id;
    @Getter @Setter private String weather;
}
