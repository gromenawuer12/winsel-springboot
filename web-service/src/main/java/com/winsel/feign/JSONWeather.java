package com.winsel.feign;

import com.winsel.dto.WeatherResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("api-service")
@CircuitBreaker(name="default",fallbackMethod = "fallback")
public interface JSONWeather {
    @RequestMapping(method = RequestMethod.GET, value = "/weather?localDateTime={localDateTime}", produces = "application/json")
    WeatherResponse getWeather(@PathVariable("localDateTime") String localDateTime);

    @RequestMapping(method = RequestMethod.GET, value = "/weather?localDateTime={localDateTime}", produces = "application/json")
    WeatherResponse fallback(@PathVariable("localDateTime") String localDateTime, IllegalArgumentException e);

    @RequestMapping(method = RequestMethod.GET, value = "/weather?localDateTime={localDateTime}", produces = "application/json")
    WeatherResponse fallback(@PathVariable("localDateTime") String localDateTime, RuntimeException  e);
}