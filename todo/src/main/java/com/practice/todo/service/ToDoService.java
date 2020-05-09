package com.practice.todo.service;

import com.practice.todo.model.ToDoModel;
import com.practice.todo.repo.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService implements ToDoServiceInterface{

    private final ToDoRepo repository;

    @Autowired
    public ToDoService(ToDoRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<ToDoModel> retrieveItems() {
        return repository.findAll();
    }

    @Override
    public ToDoModel createItem(ToDoModel item) {
        return repository.save(item);
    }

    @Override
    public boolean deleteItem(Integer id) {
        if(repository.findById(id).isPresent())
        {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
