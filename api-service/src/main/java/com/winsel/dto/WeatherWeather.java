package com.winsel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@NoArgsConstructor
public class WeatherWeather {

    @Getter @Setter private String main;

}
