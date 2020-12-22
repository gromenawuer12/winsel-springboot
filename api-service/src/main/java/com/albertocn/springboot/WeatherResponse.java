package com.albertocn.springboot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private List<WeatherList> weatherList;

    public WeatherResponse() {

    }
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public List<WeatherList> getList() {
        return this.weatherList;
    }

    public void setList(List<WeatherList> weatherList) {
        this.weatherList = weatherList;
    }

    public int findDt(long dt){
        WeatherList current = null;
        for (WeatherList next : this.weatherList) {
            if(current!=null){
                if(dt>=current.getDt() && dt<=next.getDt()){
                    return getList().indexOf(next);
                }
            }
            current = next;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Response{" +
                "list = " + weatherList +
                '}';
    }
}
