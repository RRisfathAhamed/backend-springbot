package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJobSeekerSkillsResponseDTO {

    private long id;
    private String skills;
}
