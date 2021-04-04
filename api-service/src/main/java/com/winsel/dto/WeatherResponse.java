package com.winsel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@NoArgsConstructor
public class WeatherResponse {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("list")
    private List<WeatherList> weatherList;

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
}
