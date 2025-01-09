package com.mora.jobrecommendationapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

// JobDTO.java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDTO {
    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String jobExperience;
    private String qualifiedEducation;
    private String jobSkills;
    private Date jobPostedDate;
    private Boolean isHired;
}



