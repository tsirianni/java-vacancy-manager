package org.personal.Vacancy_Manager.modules.candidate.useCases;

import org.personal.Vacancy_Manager.modules.company.entities.JobEntity;
import org.personal.Vacancy_Manager.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilter {
    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContaining(filter);
    }
}
