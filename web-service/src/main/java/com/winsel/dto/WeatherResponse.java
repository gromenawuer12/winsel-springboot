package com.winsel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<WeatherMain> weatherMain;

    private int dt;

    public WeatherResponse() {

    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public List<WeatherMain> getWeatherMain() {
        return this.weatherMain;
    }

    public void setWeatherMain(List<WeatherMain> weatherMain) {
        this.weatherMain = weatherMain;
    }

    @Override
    public String toString() {
        return "Response{" +
                " dt = " + dt +
                ", weather = " + weatherMain +
                '}';
    }
}
