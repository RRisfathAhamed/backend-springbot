package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class UpdateJobRequestDTO {

    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String jobExperience;
    private String qualifiedEducation;
    private String jobSkills;
    private Boolean isHired;
    private Long jobProviderId;
}
