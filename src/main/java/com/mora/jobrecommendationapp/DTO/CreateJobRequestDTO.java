package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateJobRequestDTO {

    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String jobExperience;
    private String qualifiedEducation;
    private String jobSkills;
    private Date jobPostedDate;
    private Boolean isHired;
    private Long jobProviderId;
}
