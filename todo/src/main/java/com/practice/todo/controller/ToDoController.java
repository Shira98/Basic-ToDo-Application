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

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping(value = "/ToDo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

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

    // Updates an existing item.
    @PutMapping
    public ResponseEntity<?> updateItem(@Valid @NotNull @RequestBody ToDoModel item ){
        ToDoModel itemExists = toDoService.updateItem(item);

        try{
            if(itemExists!=null) {
                return ResponseEntity.ok()
                        .location(new URI("/ToDo/" + itemExists.getId()))
                        .body(itemExists);
            } else{
                return ResponseEntity.notFound().build();
            }
        }catch(URISyntaxException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Returns a specific to-do item.
    @GetMapping("{id}")
    public ResponseEntity<?> retrieveItemByID(@PathVariable Integer id)
    {
        if(toDoService.retrieveItemByID(id).isPresent()){
            return ResponseEntity.ok()
                    .body(toDoService.retrieveItemByID(id));
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
