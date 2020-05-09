package com.practice.todo.controller;

import com.practice.todo.model.ToDoModel;
import com.practice.todo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/ToDo")
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    // Returns all to-do items in the database.
    @GetMapping
    public List<ToDoModel> retrieveItems(){
        return toDoService.retrieveItems();
    }

    // Creates a new item.
    @PostMapping
    public ResponseEntity<ToDoModel> createItem(@Valid @NotNull @RequestBody ToDoModel item){
        ToDoModel newItem = toDoService.createItem(item);

        try{
            return ResponseEntity
                    .created(new URI("/ToDo/"+newItem.getId()))
                    .body(newItem);
        }catch(URISyntaxException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // Deletes an existing item.
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Integer id)
    {
            if (toDoService.deleteItem(id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }

    }

}
