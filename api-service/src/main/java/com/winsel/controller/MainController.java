package com.winsel.controller;

import com.winsel.controller.exception.NoDateFoundException;
import com.winsel.dto.WeatherList;
import com.winsel.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/weather")
public class MainController {

    @Value("${app.apiLink}")
    private String apiLink;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping
    public WeatherList run(@RequestParam(value = "localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime, RestTemplate restTemplate) throws Exception {

        WeatherResponse weatherResponse = restTemplate.getForObject(apiLink, WeatherResponse.class);
        try {
            long epoch = localDateTime.toEpochSecond(ZoneOffset.of("Z"));
            if (weatherResponse.findDt(epoch) >= 0) {
                return weatherResponse.getList().get(weatherResponse.findDt(epoch));
            } else {
                throw new NoDateFoundException("3RR0R!! No date found!!");
            }
        } catch (Exception e) {
            throw new NoDateFoundException("3RR0R!! No date found!!");
        }
    }
}
