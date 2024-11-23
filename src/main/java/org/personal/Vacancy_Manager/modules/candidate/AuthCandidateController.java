package org.personal.Vacancy_Manager.modules.candidate;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.personal.Vacancy_Manager.modules.candidate.dto.AuthCandidateRequestDTO;
import org.personal.Vacancy_Manager.modules.candidate.useCases.AuthCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidates")
@Tag(name = "Auth")
public class AuthCandidateController {

    @Autowired
    private AuthCandidate authCandidate;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var token = this.authCandidate.execute(authCandidateRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }
    }
}
