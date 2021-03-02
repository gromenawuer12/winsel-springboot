package com.winsel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherList {

    private int dt;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<WeatherMain> weatherMain;

    public WeatherList() {

    }

    public List<WeatherMain> getWeather() {
        return this.weatherMain;
    }

    public void setWeather(List<WeatherMain> weatherMain) {
        this.weatherMain = weatherMain;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "List{" +
                "dt = " + dt +
                ", weather = " + weatherMain +
                '}';
    }
}