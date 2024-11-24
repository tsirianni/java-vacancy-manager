package org.personal.Vacancy_Manager.modules.candidate;

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
import org.personal.Vacancy_Manager.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.personal.Vacancy_Manager.modules.candidate.entity.ApplyForJobEntity;
import org.personal.Vacancy_Manager.modules.candidate.entity.CandidateEntity;
import org.personal.Vacancy_Manager.modules.candidate.useCases.ApplyForJob;
import org.personal.Vacancy_Manager.modules.candidate.useCases.CreateCandidate;
import org.personal.Vacancy_Manager.modules.candidate.useCases.ListAllJobsByFilter;
import org.personal.Vacancy_Manager.modules.candidate.useCases.ProfileCandidate;
import org.personal.Vacancy_Manager.modules.company.entities.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Candidate", description = "Candidate info")
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CreateCandidate createCandidate;

    @Autowired
    private ProfileCandidate profileCandidate;

    @Autowired
    private ListAllJobsByFilter listAllJobsByFilter;

    @Autowired
    private ApplyForJob applyForJob;

    @PostMapping
    @Operation(summary = "Creates a candidate")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
                    @ApiResponse(responseCode = "422")}

    )
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
        try {
            var result = createCandidate.execute(candidate);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }


    @GetMapping()
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Returns a candidate's profile")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
                    @ApiResponse(responseCode = "404")}

    )
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var candidateId = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidate.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "List openings for candidates", description = "Lists all openings available to candidates")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema =
                    @Schema(implementation = JobEntity.class)))
            })
    )
    @SecurityRequirement(name = "jwt_auth")
    @GetMapping("/jobs")
    public List<JobEntity> findJobsByFilter(@RequestParam() String filter) {
        return this.listAllJobsByFilter.execute(filter);
    }

    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/jobs/apply")
    @Operation(summary = "Apply for a job", description = "Creates a job application for a given candidate")
    public ResponseEntity<Object> applyForJob(HttpServletRequest request, @RequestBody UUID openingId) {
        var candidateId = request.getAttribute("candidate_id");

        try {
            var jobApplication = this.applyForJob.execute(UUID.fromString(candidateId.toString()), openingId);
            return ResponseEntity.ok().body(jobApplication);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
