package org.personal.Vacancy_Manager.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Junior software developer opening for java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "PLR, Gympass", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Junior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
