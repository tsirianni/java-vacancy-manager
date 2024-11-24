package org.personal.Vacancy_Manager.modules.candidate.useCases;

import org.personal.Vacancy_Manager.exceptions.JobNotFoundException;
import org.personal.Vacancy_Manager.exceptions.UserNotFoundException;
import org.personal.Vacancy_Manager.modules.candidate.CandidateRepository;
import org.personal.Vacancy_Manager.modules.candidate.entity.ApplyForJobEntity;
import org.personal.Vacancy_Manager.modules.candidate.repository.ApplyForJobRepository;
import org.personal.Vacancy_Manager.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyForJob {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    private ApplyForJobRepository applyForJobRepository;

    public ApplyForJobEntity execute(UUID candidateId, UUID openingId) {
        this.candidateRepository.findById(candidateId).orElseThrow(UserNotFoundException::new);

        this.jobRepository.findById(openingId).orElseThrow(JobNotFoundException::new);

        var jobApplication = new ApplyForJobEntity();
        jobApplication.setCandidateId(candidateId);
        jobApplication.setOpeningId(openingId);

        return this.applyForJobRepository.save(jobApplication);
    }
}
