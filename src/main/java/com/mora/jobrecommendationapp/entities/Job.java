package com.mora.jobrecommendationapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jobId;

    @Column
    private String jobTitle;

    @Column (length = 1000)
    private String jobDescription;

    @Column
    private String jobLocation;

    @Column (length = 1000)
    private String jobExperience;

    @Column (length = 1000)
    private String qualifiedEducation;

    @Column (length = 1000)
    private String jobSkills;

    @Column
    private Date jobPostedDate;

    @Column
    private Boolean isHired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_provider_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "jobs"}) // Ignores the 'jobs' field in JobProvider to prevent recursion
    private JobProvider jobProvider;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Prevents serialization of the jobs list when JobProvider is serialized
    private List<Application> applications = new ArrayList<>();


}

