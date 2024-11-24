package org.personal.Vacancy_Manager.modules.candidate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.personal.Vacancy_Manager.modules.candidate.CandidateEntity;
import org.personal.Vacancy_Manager.modules.company.entities.JobEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "apply_jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyForJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    private CandidateEntity candidateEntity;

    @ManyToOne
    @JoinColumn(name = "opening_id", insertable = false, updatable = false)
    private JobEntity jobEntity;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "opening_id")
    private UUID openingId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
