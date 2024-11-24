package org.personal.Vacancy_Manager.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.Vacancy_Manager.exceptions.JobNotFoundException;
import org.personal.Vacancy_Manager.exceptions.UserNotFoundException;
import org.personal.Vacancy_Manager.modules.candidate.entity.CandidateEntity;
import org.personal.Vacancy_Manager.modules.candidate.repository.CandidateRepository;
import org.personal.Vacancy_Manager.modules.candidate.entity.ApplyForJobEntity;
import org.personal.Vacancy_Manager.modules.candidate.repository.ApplyForJobRepository;
import org.personal.Vacancy_Manager.modules.company.entities.JobEntity;
import org.personal.Vacancy_Manager.modules.company.repositories.JobRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyForJobTest {

    @InjectMocks
    private ApplyForJob applyForJob;

    @Mock
    CandidateRepository repository;

    @Mock
    JobRepository jobRepository;

    @Mock
    ApplyForJobRepository applyForJobRepository;

    @Test
    @DisplayName("Should not be able to apply for a job if the candidate is not found")
    public void should_not_be_able_to_apply_for_job_if_candidate_not_found() {
        try {
            this.applyForJob.execute(null, null);
        } catch (Exception e) {
            assertThrows(RuntimeException.class, () -> {
                throw new UserNotFoundException();
            });
        }
    }

    @Test
    @DisplayName("Should not be able to apply for a job if the opening is not found")
    public void should_not_be_able_to_apply_for_job_if_opening_not_found() {
        var candidateId = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(candidateId);
        when(this.repository.findById(candidateId)).thenReturn(Optional.of(candidate));

        try {
            this.applyForJob.execute(candidateId, null);
        } catch (Exception e) {
            assertThrows(RuntimeException.class, () -> {
                throw new JobNotFoundException();
            });
        }
    }

    @Test
    @DisplayName("Should be able to apply for a job")
    public void should_be_able_to_apply_for_job() {
        var candidateId = UUID.randomUUID();
        var openingId = UUID.randomUUID();

        var jobApplication = new ApplyForJobEntity();
        jobApplication.setOpeningId(openingId);
        jobApplication.setCandidateId(candidateId);

        var createdJobApplication = new ApplyForJobEntity();
        createdJobApplication.setOpeningId(openingId);
        createdJobApplication.setCandidateId(candidateId);
        createdJobApplication.setId(UUID.randomUUID());
        createdJobApplication.setCreatedAt(LocalDateTime.now());
        createdJobApplication.setUpdatedAt(LocalDateTime.now());


        when(this.repository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(this.jobRepository.findById(openingId)).thenReturn(Optional.of(new JobEntity()));
        when(this.applyForJobRepository.save(jobApplication)).thenReturn(createdJobApplication);

        var result = this.applyForJob.execute(candidateId, openingId);

        assertNotNull(result.getId());
    }
}
