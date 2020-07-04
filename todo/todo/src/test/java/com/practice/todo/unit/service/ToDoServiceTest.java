package com.practice.todo.unit.service;

import com.practice.todo.model.ToDoModel;
import com.practice.todo.repo.ToDoRepo;
import com.practice.todo.service.ToDoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ToDoServiceTest {

    @Autowired
    private ToDoService service;

    @MockBean
    private ToDoRepo repository;

    @Test
    void testRetrieveItems() {

        //Setup mock repository:
        ToDoModel mockItem1 = new ToDoModel(1,"Buy Groceries", false);
        ToDoModel mockItem2 = new ToDoModel(2,"Buy Tablet", false);

        when(repository.findAll()).thenReturn(Arrays.asList(mockItem1, mockItem2));

        //Execute the service call:
        List<ToDoModel> items = service.retrieveItems();

        //Assertions:
        Assertions.assertEquals(2, items.size(), "Expected number of items is 2");
    }

    @Test
    void testCreateItem() {

        //Setup mock repository:
        ToDoModel mockItem = new ToDoModel();
        mockItem.setItem("Buy Groceries");

        ToDoModel savedItem = new ToDoModel(1, "Buy Groceries", false);

        when(repository.save(any())).thenReturn(savedItem);

        //Execute the service call:
        ToDoModel returnedItem = service.createItem(mockItem);

        //Assertions:
        Assertions.assertNotNull(returnedItem, "The saved product should not be null");
        Assertions.assertFalse(returnedItem.isCompleted(), "The To-do item should not be done when created i.e. \"completed\" must be false");
    }

    @Test
    void testDeleteItemSuccess() {

        //Setup mock repository:
        ToDoModel mockItem1 = new ToDoModel(1,"Buy Groceries", false);

        when(repository.findById(1)).thenReturn(Optional.of(mockItem1));

        //Execute the service call:
        boolean result = service.deleteItem(1);

        //Assertions:
        Assertions.assertTrue(result, "Failed to delete item");

    }

    @Test
    void testDeleteItemNotFound (){

        //Setup mock repository:
        when(repository.findById(1)).thenReturn(Optional.empty());

        //Execute the service call:
        boolean result = service.deleteItem(1);

        //Assertions:
        Assertions.assertFalse(result, "Found item that doesn't exist");
    }

    @Test
    void testUpdateItemSuccess(){

        //Setup mock repository:
        ToDoModel mockItem = new ToDoModel(1, "Buy Groceries", false);
        ToDoModel updatedItem = new ToDoModel(1, "Buy Groceries", true);

        when(repository.findById(1)).thenReturn(Optional.of(mockItem));
        when(repository.save(any())).thenReturn(updatedItem);

        //Execute the service call:
        ToDoModel returnedItem = service.updateItem(updatedItem);

        //Assertions:
        Assertions.assertNotNull(returnedItem);
        Assertions.assertTrue(returnedItem.isCompleted());


    }

    @Test
    void testUpdateItemNotFound(){

        //Setup mock repository:
        ToDoModel mockItem = new ToDoModel(3, "Buy Groceries", false);

        when(repository.findById(3)).thenReturn(Optional.empty());

        //Execute the service call:
        ToDoModel returnedItem = service.updateItem(mockItem);

        //Assertions:
        Assertions.assertNull(returnedItem);
    }

    @Test
    void testRetrieveItemByIDSuccess(){

        //Setup mock repository:
        ToDoModel mockItem = new ToDoModel(1, "Buy Groceries", false);

        when(repository.findById(1)).thenReturn(Optional.of(mockItem));

        //Execute the service call:
        Optional<ToDoModel> returnedItem = service.retrieveItemByID(1);

        //Assertions:
        Assertions.assertTrue(returnedItem.isPresent());
        Assertions.assertEquals(returnedItem.get(), mockItem);

    }

    @Test
    void testRetrieveItemByIDNotFound(){

        //Setup mock repository:
        when(repository.findById(1)).thenReturn(Optional.empty());

        //Execute the service call:
        Optional<ToDoModel> returnedItem = service.retrieveItemByID(1);

        //Assertions:
        Assertions.assertFalse(returnedItem.isPresent() );

    }
}