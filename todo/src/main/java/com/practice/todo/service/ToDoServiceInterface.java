package com.practice.todo.service;

import com.practice.todo.model.ToDoModel;

import java.util.List;
import java.util.Optional;

public interface ToDoServiceInterface {

    /*
    * Retrieves the list of all the To-do items.
    * */
    List<ToDoModel> retrieveItems();

    /*
    * Creates a new To-do item.
    * */
    ToDoModel createItem(ToDoModel item);

    /*
    * Deletes an existing To-do item.
    * */
    boolean deleteItem(Integer id);

    /*
    * Retrieves the To-Do item by ID.
    * */
    Optional<ToDoModel> retrieveItemByID(Integer id);

    /*
    * Updates an existing To-Do item by ID.
    * */
    ToDoModel updateItem(ToDoModel item );
}
