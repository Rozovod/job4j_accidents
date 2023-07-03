package ru.job4j.accidents.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.job4j.accidents.Job4jAccidentsApplication;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Job4jAccidentsApplication.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {
    @MockBean
    private AccidentService accidents;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenReturnCreateAccidentPage() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/create"));
    }

    @Test
    @WithMockUser
    public void whenReturnEditAccidentPage() throws Exception {
        int id = 1;
        when(accidents.findById(id)).thenReturn(Optional.of(
                new Accident(1, "accident", "text", "address",
                        new AccidentType(1, "type"), new HashSet<>(List.of(new Rule(1, "name"))))));
        this.mockMvc.perform(get("/editAccident/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/edit"));
    }
}
