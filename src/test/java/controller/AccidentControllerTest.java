package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Job4jAccidentsApplication;

@SpringBootTest(classes = Job4jAccidentsApplication.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {
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
        this.mockMvc.perform(get("/editAccident/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/edit"));
    }
}
