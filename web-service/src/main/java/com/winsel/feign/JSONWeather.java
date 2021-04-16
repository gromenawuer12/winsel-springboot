package com.winsel.feign;

import com.winsel.dto.WeatherResponse;
import com.winsel.ribbon.RibbonConfiguration;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("api-service")
@CircuitBreaker(name="api-service")
@RibbonClient(name="myRibbonClient",configuration = {RibbonConfiguration.class})
public interface JSONWeather {
    @RequestMapping(method = RequestMethod.GET, value = "/weather?localDateTime={localDateTime}", produces = "application/json")
    WeatherResponse getWeather(@PathVariable("localDateTime") String localDateTime);
}
