package org.personal.Vacancy_Manager.modules.company.useCases;

import org.personal.Vacancy_Manager.exceptions.UserFoundException;
import org.personal.Vacancy_Manager.modules.company.entities.CompanyEntity;
import org.personal.Vacancy_Manager.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CreateCompany {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity company) {
        this.repository.findByUsernameOrEmail(company.getUsername(), company.getEmail()).ifPresent((user) -> {
            throw new UserFoundException();
        });

        var password = passwordEncoder.encode(company.getPassword());
        company.setPassword(password);

        CompanyEntity newCompany = null;
        try {
            newCompany = this.repository.save(company);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return newCompany;
    }
}
