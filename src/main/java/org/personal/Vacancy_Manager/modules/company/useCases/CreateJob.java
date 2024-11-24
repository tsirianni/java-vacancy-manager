package org.personal.Vacancy_Manager.modules.company.useCases;

import org.personal.Vacancy_Manager.exceptions.CompanyNotFoundException;
import org.personal.Vacancy_Manager.modules.company.entities.JobEntity;
import org.personal.Vacancy_Manager.modules.company.repositories.CompanyRepository;
import org.personal.Vacancy_Manager.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJob {

    @Autowired
    private JobRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {
        this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(CompanyNotFoundException::new);

        return this.repository.save(jobEntity);
    }
}
