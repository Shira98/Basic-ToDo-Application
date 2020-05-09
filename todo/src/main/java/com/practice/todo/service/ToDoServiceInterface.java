package com.practice.todo.service;

import com.practice.todo.model.ToDoModel;

import java.util.List;

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

}
