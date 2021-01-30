package com.albertocn.springboot;

import org.springframework.data.repository.CrudRepository;

public interface WeatherTaskRepository extends CrudRepository<WeatherTask, Integer> {
}
