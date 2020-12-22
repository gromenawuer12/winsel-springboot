package com.albertocn.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootApplication
@RequestMapping(path="/tasks")
public class ConsumingRestApplication {
    @Value("${app.apiLink}")
    private String apiLink;

    private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumingRestApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    @GetMapping(path = "/weather")
    @ResponseBody
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

