package org.personal.Vacancy_Manager.modules.company.useCases;

import org.personal.Vacancy_Manager.modules.company.entities.JobEntity;
import org.personal.Vacancy_Manager.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJob {

    @Autowired
    private JobRepository repository;

    public JobEntity execute(JobEntity jobEntity) {
        return this.repository.save(jobEntity);
    }
}
