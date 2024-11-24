package org.personal.Vacancy_Manager.modules.company.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.personal.Vacancy_Manager.modules.company.dto.CreateJobDTO;
import org.personal.Vacancy_Manager.modules.company.entities.CompanyEntity;
import org.personal.Vacancy_Manager.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.personal.Vacancy_Manager.utils.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestCreateJobController {

    private MockMvc mockMvc;
    private CompanyEntity testCompany;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .apply(SecurityMockMvcConfigurers.springSecurity())
                                 .build();

        testCompany = companyRepository.saveAndFlush(generateTestCompany());
    }

    @Test
    @DisplayName("Should be able to create a new opening")
    public void should_be_able_to_create_a_new_opening() throws Exception {

        var dto = new CreateJobDTO("Test Opening", "PLR", "JUNIOR");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/companies/openings")
                                                           .contentType(MediaType.APPLICATION_JSON)
                                                           .content(objectToJson(dto))
                                                           .header("Authorization", "Bearer " + generateToken(
                                                                   testCompany.getId(),
                                                                   "JAVAS_@123#"
                                                           ))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
