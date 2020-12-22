package com.albertocn.springboot;

import org.springframework.data.repository.CrudRepository;

public interface TasksRepository extends CrudRepository<Tasks, Integer> {
}
