package org.personal.Vacancy_Manager.modules.candidate.useCases;

import org.personal.Vacancy_Manager.exceptions.UserNotFoundException;
import org.personal.Vacancy_Manager.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.personal.Vacancy_Manager.modules.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidate {

    @Autowired
    private CandidateRepository repository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = this.repository.findById(candidateId).orElseThrow(UserNotFoundException::new);

        return new ProfileCandidateResponseDTO(candidate.getDescription(), candidate.getUsername(), candidate.getEmail(),
                                               candidate.getId(), candidate.getName()
        );
    }
}
