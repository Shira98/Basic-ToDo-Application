package com.practice.todo.unit.repo;

import com.practice.todo.model.ToDoModel;
import com.practice.todo.repo.ToDoRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToDoRepoTest {

    @Autowired
    private ToDoRepo repository;

    //Pre-load the database with a single item before each test case:
    @BeforeEach
    public void loadTestData() {
        ToDoModel item = new ToDoModel();
        item.setItem("Buy Groceries");
        repository.save(item);
    }

    @Test
    void testSaveItemSuccess(){

        //Create a new item and save it in the database:
        ToDoModel item = new ToDoModel();
        item.setItem("Buy TV");

        ToDoModel itemSaved = repository.save(item);

        //Validate saved product:
        Assertions.assertEquals("Buy TV", itemSaved.getItem());
        Assertions.assertFalse(itemSaved.isCompleted());

        //Validate that we can get it back out of the database:
        Optional<ToDoModel> loadedItem = repository.findById(itemSaved.getId());

        Assertions.assertTrue(loadedItem.isPresent(), "Could not reload item from the database");
        Assertions.assertEquals("Buy TV", loadedItem.get().getItem(), "Item name does not match");
        Assertions.assertFalse(loadedItem.get().isCompleted(), "Item completed status should be \"false\"");

    }

    @Test
    void testFindAllItems() {

        //Retrieve items present in the database:
        List<ToDoModel> items = repository.findAll();

        //Validate the number of items:
        Assertions.assertEquals(1, items.size(), "We should have 1 item in our database");
    }

    @Test
    void testFindByIdSuccess() {

        //Find the item with ID 1:
        Optional<ToDoModel> item = repository.findById(1);

        //Validate that it's found:
        Assertions.assertTrue(item.isPresent(), "Item with ID 1 should be found");

        //Validate the item's values:
        Assertions.assertEquals(1, item.get().getId() , "Item ID should be 1");
        Assertions.assertEquals("Buy Groceries", item.get().getItem(), "Item name should be \"Buy Groceries\"");
        Assertions.assertFalse(item.get().isCompleted(), "Item's completed status should be \"false\"");
    }

    @Test
    void testFindByIdNotFound() {

        //Try to retrieve the item with ID 3:
        Optional<ToDoModel> item = repository.findById(3);

        //Validate that it isn't found:
        Assertions.assertFalse(item.isPresent(), "Item with ID 3 should be not be found");
    }

    @Test
    void testDeleteSuccess() {

        //Delete the item with ID 1:
        repository.deleteById(1);

        //Validate that the item has been deleted:
        Optional<ToDoModel> loadedItem = repository.findById(1);

        Assertions.assertFalse(loadedItem.isPresent(), "The item should be deleted successfully");
    }

    @Test
    void testDeleteFailure() {

        //Try to delete the item with ID 4 i.e. expecting an exception:
        Exception expect = Assertions.assertThrows(EmptyResultDataAccessException.class,() -> repository.deleteById(4));

        String expectedMessage = "No class com.practice.todo.model.ToDoModel entity with id 4 exists!";
        String actualMessage = expect.getMessage();

        //Validate the exception thrown:
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}