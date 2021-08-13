package jdanimal.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


public class TestWebApp extends DemoApplicationTests{

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUserRegistration() throws Exception {
        mockMvc.perform(get("/reg")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value("Jordan"))
                .andExpect(jsonPath("$.password").value("22"))
                .andExpect(jsonPath("$.confirmPassword").value("22"))
                .andExpect(jsonPath("$.email").value("jordan@abv.bg"))
                .andExpect(jsonPath("$.postcode").value("4445"))
                .andExpect(jsonPath("$.phoneNumber").value("089665885"));

    }

    @Test
    public void testUploadAnimal() throws Exception {
        mockMvc.perform(get("/animalupload")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.ageOfAnimal").value("3"))
                .andExpect(jsonPath("$.foodOfAnimal").value("Meat"))
                .andExpect(jsonPath("$.kilogramsOfAnimal").value("15"))
                .andExpect(jsonPath("$.typeOfAnimal").value("Golden Retriver"))
                .andExpect(jsonPath("$.nameOfAnimal").value("Rayan"));
    }
}
