package com.practice.todo.repo;

import com.practice.todo.model.ToDoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepo extends JpaRepository<ToDoModel, Integer> {

}
