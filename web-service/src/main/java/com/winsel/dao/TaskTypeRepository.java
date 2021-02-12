package com.winsel.dao;

import com.winsel.dao.entity.TaskType;
import org.springframework.data.repository.CrudRepository;

public interface TaskTypeRepository extends CrudRepository<TaskType, Integer> {
}
