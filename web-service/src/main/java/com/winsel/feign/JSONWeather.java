package com.winsel.feign;

import com.winsel.dto.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("api-service")
public interface JSONWeather {
    @RequestMapping(method = RequestMethod.GET, value = "/weather?localDateTime={localDateTime}", produces = "application/json")
    WeatherResponse getWeather(@PathVariable("localDateTime") String localDateTime);
}
