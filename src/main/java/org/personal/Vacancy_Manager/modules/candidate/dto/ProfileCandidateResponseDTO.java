package org.personal.Vacancy_Manager.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ProfileCandidateResponseDTO(
        @Schema(example = "Java developer")
        String description,

        @Schema(example = "code_wMark")
        String username,

        @Schema(example = "mark.watson@coders.io")
        String email,
        UUID id,

        @Schema(example = "Mark")
        String name) {
}
