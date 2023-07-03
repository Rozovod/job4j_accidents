package ru.job4j.accidents.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @WithMockUser
    public void whenSaveAccident() throws Exception {
        Accident accident = new Accident();
        accident.setName("test");
        int typeId = 1;
        List<Integer> rIds = List.of(1, 2);
        this.mockMvc.perform(post("/saveAccident")
                        .flashAttr("accident", accident)
                        .param("type.id", String.valueOf(typeId))
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("success/success"))
                .andExpect(model().attributeExists("message"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).create(argument.capture(), eq(typeId), eq(rIds));
        assertThat(argument.getValue().getName()).isEqualTo("test");
    }

    @Test
    @WithMockUser
    public void whenUpdateAccident() throws Exception {
        Accident accident = new Accident();
        accident.setName("test");

        int typeId = 1;
        List<Integer> rIds = List.of(1, 2);

        this.mockMvc.perform(post("/updateAccident")
                        .flashAttr("accident", accident)
                        .param("type.id", String.valueOf(typeId))
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("success/success"))
                .andExpect(model().attributeExists("message"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);

        verify(accidents).update(argument.capture(), eq(typeId), eq(rIds));
        assertThat(argument.getValue().getName()).isEqualTo("test");
    }
}
