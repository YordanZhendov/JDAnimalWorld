package jdanimal.demo.web.controllers;

import jdanimal.demo.DemoApplication;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    void register() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

}