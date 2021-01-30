package com.albertocn.springboot;

import javax.persistence.*;

@Entity
@Table(name = "weather_tasks")
public class WeatherTask {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String weather;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
