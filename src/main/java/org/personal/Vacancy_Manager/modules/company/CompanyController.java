package org.personal.Vacancy_Manager.modules.company;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.personal.Vacancy_Manager.modules.company.entities.CompanyEntity;
import org.personal.Vacancy_Manager.modules.company.useCases.CreateCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
@Tag(name = "Companies", description = "Route details for company-related routes")
public class CompanyController {

    @Autowired
    private CreateCompany createCompany;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company) {
        try {
            var result = createCompany.execute(company);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
