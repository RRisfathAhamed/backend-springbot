package com.mora.jobrecommendationapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobMatchDTO {
    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String jobExperience;
    private String qualifiedEducation;
    private String jobSkills;
    private Date jobPostedDate;
    private Boolean isHired;
    private Double matchPercentage;
    private String comment;
}
