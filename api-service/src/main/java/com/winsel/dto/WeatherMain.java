package com.winsel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMain {

    private String main;

    public WeatherMain() {

    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "main = " + main +
                '}';
    }

}
