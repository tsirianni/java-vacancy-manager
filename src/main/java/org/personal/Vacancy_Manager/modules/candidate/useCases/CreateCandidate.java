package org.personal.Vacancy_Manager.modules.candidate.useCases;

import org.personal.Vacancy_Manager.exceptions.UserFoundException;
import org.personal.Vacancy_Manager.modules.candidate.entity.CandidateEntity;
import org.personal.Vacancy_Manager.modules.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidate {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateRepository repository;

    public CandidateEntity execute(CandidateEntity candidate) {
        this.repository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail()).ifPresent((user) -> {
            throw new UserFoundException();
        });

        var password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);

        return this.repository.save(candidate);
    }
}
