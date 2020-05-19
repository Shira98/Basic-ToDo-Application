package com.practice.todo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.todo.model.ToDoModel;
import com.practice.todo.repo.ToDoRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ToDoIntegrationTest {

    @Autowired
    private ToDoRepo repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // Pre-load the database with a single item before each test case:
    @BeforeEach
    public void loadTestData() {
        ToDoModel item = new ToDoModel();
        item.setItem("Buy Groceries");
        repository.save(item);
    }

    @Test
    void testRetrieveItems() throws Exception{

        // Perform the GET request:
        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ToDo",  String.class);

        String expected = "[{ \"id\":1,\"item\":\"Buy Groceries\",\"completed\":false }]";

        // Assertions:
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    void testCreateItemSuccess() throws Exception {

        // Set up item to create:
        ToDoModel item = new ToDoModel();
        item.setItem("Buy TV");

        // Perform the POST request:
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/ToDo", item, String.class);

        String expected = "{ \"id\":2,\"item\":\"Buy TV\",\"completed\":false }";

        // Assertions:
        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false );

        // Double check this new vehicle has been stored:
        Optional<ToDoModel> returnedItem = repository.findById(2);
        Assertions.assertTrue(returnedItem.isPresent());
        Assertions.assertEquals(2, returnedItem.get().getId());
    }

    @Test
    void testCreateItemNullFailure() throws Exception{

        // Set up a null item to create:
        ToDoModel item = new ToDoModel();

        // Perform the POST request:
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/ToDo", item, String.class);

        // Assertions:
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void testCreateItemFieldFailure() throws Exception {

        // Set up item to create with a bad JSON string:
        String Item = "{\"name\":\"John\", \"number\":879, \"completed\":false}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(Item , headers);

        // Perform the POST request:
        ResponseEntity<String> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + "/ToDo", HttpMethod.POST, request, String.class);

        // Assertions:
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void testDeleteItemSuccess() throws Exception {

        // Perform the DELETE request:
        ResponseEntity<Object> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + "/ToDo/1", HttpMethod.DELETE, null, Object.class);

        // Assertions:
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());

        // Double check the item has been deleted:
        Optional<ToDoModel> optional = repository.findById(1);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void testDeleteItemNotFound() throws Exception {

        // Perform the DELETE request for a non-existent item:
        ResponseEntity<Object> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + "/ToDo/10", HttpMethod.DELETE, null, Object.class);

        // Assertions:
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
    }

    @Test
    void testUpdateItemSuccess() throws Exception{

        // Set up item to update its "completed" & "item" attributes to "true" & "Buy Veggies":
        ToDoModel item = new ToDoModel(1,"Buy Veggies", true);

        // Perform the PUT request:
        this.restTemplate
                .put("http://localhost:" + port + "/ToDo", item, Object.class);

        String expected = "{ \"id\":1,\"item\":\"Buy Veggies\",\"completed\":true }";

        // Double check the item has been updated:
        Optional<ToDoModel> returnedItem = repository.findById(1);

        // Assertions:
        Assertions.assertEquals(1, returnedItem.get().getId());
        Assertions.assertEquals("Buy Veggies", returnedItem.get().getItem());
        Assertions.assertTrue(returnedItem.get().isCompleted());

    }

    @Test
    void testUpdateItemNotFound() throws Exception{

        // Set up item to update with non-existent ID:
        ToDoModel item = new ToDoModel(12,"Buy Groceries", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(asJsonString(item), headers);

        // Perform the PUT request:
        ResponseEntity<String> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + "/ToDo", HttpMethod.PUT, request, String.class );

        // Assertions:
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    void testUpdateItemFailure() throws Exception{

        // Set up item to update with a bad JSON string:
        String Item = "{\"id\":1, \"number\":879, \"completed\":false}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(Item , headers);

        // Perform the POST request:
        ResponseEntity<String> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + "/ToDo", HttpMethod.PUT, request, String.class);

        // Assertions:
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void testRetrieveItemByIDSuccess() throws Exception{

        // Perform the GET request:
        ResponseEntity<ToDoModel> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ToDo/1", ToDoModel.class );

        // Assertions:
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(1, responseEntity.getBody().getId() );
        Assertions.assertEquals("Buy Groceries", responseEntity.getBody().getItem() );
        Assertions.assertFalse(responseEntity.getBody().isCompleted());
    }

    @Test
    void testRetrieveItemByIDNotFound() throws Exception {

        // Perform the GET request:
        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ToDo/10",  String.class );

        // Assertions:
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode() );
        Assertions.assertNull(responseEntity.getBody());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
