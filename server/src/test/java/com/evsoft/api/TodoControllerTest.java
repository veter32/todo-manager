package com.evsoft.api;

import com.evsoft.model.Todo;
import com.evsoft.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testGetAllTodos_empty() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testAddTodo() throws Exception {
        String json = "{\"title\": \"Test Todo\", \"completed\": false}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Test Todo")))
                .andExpect(jsonPath("$.completed", is(false)));
    }

    @Test
    void testUpdateTodo() throws Exception {
        Todo t = repository.save(new Todo(null, "Old Title", false));
        String updatedJson = "{\"title\": \"New Title\", \"completed\": true}";

        mockMvc.perform(put("/api/todos/" + t.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Title")))
                .andExpect(jsonPath("$.completed", is(true)));
    }

    @Test
    void testDeleteTodo() throws Exception {
        Todo t = repository.save(new Todo(null, "Delete Me", false));

        mockMvc.perform(delete("/api/todos/" + t.getId()))
                .andExpect(status().isNoContent());
    }
}