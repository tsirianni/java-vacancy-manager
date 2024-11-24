package org.personal.Vacancy_Manager.modules.company.controllers;

import lombok.Getter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.personal.Vacancy_Manager.modules.company.dto.CreateJobDTO;
import org.personal.Vacancy_Manager.modules.company.entities.CompanyEntity;
import org.personal.Vacancy_Manager.modules.company.repositories.CompanyRepository;
import org.personal.Vacancy_Manager.utils.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${security.token.secret}")
    private String secret;

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

        mockMvc.perform(MockMvcRequestBuilders.post("/companies/openings")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectToJson(dto))
                                              .header("Authorization", "Bearer " + generateToken(
                                                      testCompany.getId(),
                                                      this.secret
                                              ))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not be able create an opening if the company does not exist")
    public void should_not_be_able_to_create_an_opening_if_the_company_does_not_exist() throws Exception {

        var dto = new CreateJobDTO("Test Opening", "PLR", "JUNIOR");

        mockMvc.perform(MockMvcRequestBuilders.post("/companies/openings")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectToJson(dto))
                                              .header("Authorization", "Bearer " + generateToken(
                                                      UUID.randomUUID(),
                                                      this.secret
                                              ))
        ).andExpect(MockMvcResultMatchers.status().is(422));
    }
}
