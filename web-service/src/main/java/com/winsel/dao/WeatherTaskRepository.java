package com.winsel.dao;

import com.winsel.dao.entity.TaskType;
import com.winsel.dao.entity.WeatherTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherTaskRepository extends CrudRepository<WeatherTask, Integer> {
    WeatherTask findByWeather(String weather);
}
