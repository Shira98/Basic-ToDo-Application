package com.practice.todo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.todo.model.ToDoModel;
import com.practice.todo.repo.ToDoRepo;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ToDoIntegrationTest {

    @Autowired
    private MockMvc mock;

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
    void testRetrieveItem() throws Exception{

        //Perform the GET request:
        mock.perform(get("/ToDo"))
                //Validate response code
                .andExpect(status().isOk())
                .andDo(print( ))

                //Validate the returned fields:
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", Is.is(1)))
                .andExpect(jsonPath("$[0].item", Is.is("Buy Groceries")))
                .andExpect(jsonPath("$[0].completed", Is.is(false)));
    }

    @Test
    void testCreateItem() throws Exception {

        //Setup product to create:
        ToDoModel item = new ToDoModel();
        item.setItem("Buy TV");

        //Perform the POST request:
        mock.perform(post("/ToDo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(item)))

                //Validate the response code and content type:
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                //Validate the returned fields:
                .andExpect(jsonPath("$.id", any(Integer.class)))
                .andExpect(jsonPath("$.item", is("Buy TV")))
                .andDo(print())
                .andExpect(jsonPath("$.completed", is(false)));
    }

    @Test
    void testProductDeleteSuccess() throws Exception {

        //Perform the DELETE request and validate the response code:
        mock.perform(delete("/ToDo/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testProductDeleteNotFound() throws Exception {

        //Perform the DELETE request and validate the response code:
        mock.perform(delete("/ToDo/{id}", 99))
                .andExpect(status().isNotFound());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
