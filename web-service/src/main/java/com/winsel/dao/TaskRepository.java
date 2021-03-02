package com.winsel.dao;

import com.winsel.dao.entity.Task;
import com.winsel.dao.entity.TaskType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByStartBetweenAndTaskTypeId(LocalDateTime from, LocalDateTime to, TaskType type);
    List<Task> findByStartBetween(LocalDateTime from, LocalDateTime to);
    List<Task> findByTaskTypeId(TaskType type);
}
