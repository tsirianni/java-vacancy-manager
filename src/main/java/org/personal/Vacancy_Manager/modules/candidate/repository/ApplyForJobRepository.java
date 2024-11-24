package org.personal.Vacancy_Manager.modules.candidate.repository;

import org.personal.Vacancy_Manager.modules.candidate.entity.ApplyForJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyForJobRepository extends JpaRepository<ApplyForJobEntity, UUID> {
}
