package org.personal.Vacancy_Manager.modules.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.personal.Vacancy_Manager.modules.company.dto.CreateJobDTO;
import org.personal.Vacancy_Manager.modules.company.entities.JobEntity;
import org.personal.Vacancy_Manager.modules.company.useCases.CreateJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/companies/openings")
@Tag(name = "Companies", description = "Route details for company-related routes")
public class JobController {

    @Autowired
    private CreateJob createJob;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary = "Creates new opening", description = "Creates a new opening for a given company")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class))
            })
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO jobDTO, HttpServletRequest request) {

        var jobEntity =
                JobEntity.builder()
                         .benefits(jobDTO.getBenefits())
                         .description(jobDTO.getDescription())
                         .level(jobDTO.getLevel())
                         .companyId(UUID.fromString(request.getAttribute("company_id").toString()))
                         .build();

        try {
            var result = createJob.execute(jobEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
