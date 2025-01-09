package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateJobSeekerSkillsRequestDTO {

    private String education;
    private String experience;
    private String skills;
}
