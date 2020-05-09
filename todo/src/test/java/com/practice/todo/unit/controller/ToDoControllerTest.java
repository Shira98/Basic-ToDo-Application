package com.practice.todo.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.todo.model.ToDoModel;
import com.practice.todo.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

    @MockBean
    private ToDoService service;

    @Autowired
    private MockMvc mock;

    @Test
    public void testRetrieveItems() throws Exception{

        //Setup mock service:
        ToDoModel item1 = new ToDoModel(1,"Buy Groceries", false);
        ToDoModel item2 = new ToDoModel(2,"Buy TV", false);

        when(service.retrieveItems()).thenReturn(Arrays.asList(item1, item2));

        //Execute the GET request:
        mock.perform(get("/ToDo"))

                //Validate response code:
                .andExpect(status().isOk())
                .andDo(print( ))

                //Validate the returned fields:
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].item", is("Buy Groceries")))
                .andExpect(jsonPath("$[0].completed", is(false)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].item", is("Buy TV")))
                .andExpect(jsonPath("$[1].completed", is(false)));

    }

    @Test
    public void testCreateItem() throws Exception{

        //Setup mock service:
        ToDoModel postItem = new ToDoModel();
        postItem.setItem("Buy Groceries");
        ToDoModel mockItem = new ToDoModel(1,"Buy Groceries", false);

        when(service.createItem(any())).thenReturn(mockItem);

        //Execute the POST request:
        mock.perform(post("/ToDo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postItem)))

                //Validate response code:
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON) )
                .andDo(print( ))

                // Validate the returned fields:
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.item", is("Buy Groceries")))
                .andExpect(jsonPath("$.completed", is(false)));
    }

    @Test
    public void testDeleteItemSuccess() throws Exception{

        //Setup mock service:
        ToDoModel mockItem = new ToDoModel(1,"Buy Groceries", false);

        when(service.deleteItem(1)).thenReturn(true);

        // Execute the DELETE request:
        mock.perform(delete("/ToDo/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testDeleteItemNotFound() throws Exception {

        //Setup mock service:
        when(service.deleteItem(1)).thenReturn(false);

        // Execute the DELETE request:
        mock.perform(delete("/ToDo/{id}", 1))
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