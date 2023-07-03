package ru.job4j.accidents.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import ru.job4j.accidents.Job4jAccidentsApplication;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

@SpringBootTest(classes = Job4jAccidentsApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService users;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenReturnLoginPage() throws Exception {
        this.mockMvc.perform(get("/users/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    @WithMockUser
    public void whenReturnRegPage() throws Exception {
        this.mockMvc.perform(get("/users/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/reg"));
    }

    @Test
    @WithMockUser
    public void whenCreateUser() throws Exception {
        this.mockMvc.perform(post("/users/reg")
                        .param("username", "user"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).save(argument.capture());
        assertThat(argument.getValue().getUsername()).isEqualTo("user");
    }
}
