package com.winsel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @Getter @Setter private int dt;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("weather")
    private List<WeatherWeather> weatherWeather;

    public List<WeatherWeather> getWeatherWeather() { return this.weatherWeather; }

    public void setWeatherMain(List<WeatherWeather> weatherWeather) {
        this.weatherWeather = weatherWeather;
    }

}
