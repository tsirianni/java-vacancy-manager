package org.personal.Vacancy_Manager.modules.candidate.useCases;

import org.personal.Vacancy_Manager.modules.candidate.repository.CandidateRepository;
import org.personal.Vacancy_Manager.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidate {

    @Autowired
    private CandidateRepository repository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = this.repository.findById(candidateId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new ProfileCandidateResponseDTO(candidate.getDescription(), candidate.getUsername(), candidate.getEmail(),
                                               candidate.getId(), candidate.getName()
        );
    }
}
