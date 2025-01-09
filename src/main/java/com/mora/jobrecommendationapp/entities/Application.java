package com.mora.jobrecommendationapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long applicationId;
    @Column
    private Boolean applicationStatus;
    @Column
    private Date jobAppliedDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applications"})
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applications"})
    private JobSeeker jobSeeker;
}
