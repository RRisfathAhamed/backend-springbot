package com.mora.jobrecommendationapp.controllers;

import com.mora.jobrecommendationapp.DTO.CreateApplicationRequestDTO;
import com.mora.jobrecommendationapp.DTO.CreateApplicationResponseDTO;
import com.mora.jobrecommendationapp.DTO.DeleteApplicationRequestDTO;
import com.mora.jobrecommendationapp.DTO.DeleteApplicationResponseDTO;
import com.mora.jobrecommendationapp.entities.Application;
import com.mora.jobrecommendationapp.entities.Job;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import com.mora.jobrecommendationapp.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<CreateApplicationResponseDTO> createApplication(@RequestBody CreateApplicationRequestDTO createApplicationRequestDTO) {
        return ResponseEntity.ok(applicationService.createApplication(createApplicationRequestDTO));
    }

        @GetMapping("/count/{jobId}")
        public Long getApplicationCountByJobId(@PathVariable Long jobId) {
            return applicationService.getApplicationCountByJobId(jobId);
        }

    @GetMapping("/countjobseeker/{jobSeekerId}")
    public long getApplicationCountByJobSeekerId(@PathVariable Long jobSeekerId) {
        return applicationService.getApplicationCountByJobSeekerId(jobSeekerId);
    }

    @GetMapping("/jobs/{jobSeekerId}")
    public List<Job> getJobsByJobSeekerId(@PathVariable Long jobSeekerId) {
        return applicationService.getJobsByJobSeekerId(jobSeekerId);
    }

    @GetMapping("/all")
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("job-seeker/{jobId}")
    public List<JobSeeker> getApplicationsByJobId(@PathVariable Long jobId) {
        return applicationService.getApplicationsByJobId(jobId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteApplicationResponseDTO> deleteApplicationById(@PathVariable("id") Long applicationId) {
        return ResponseEntity.ok(applicationService.deleteApplicationById(applicationId));
    }

    @DeleteMapping("deletebyjobidandjobseekerid")
    public ResponseEntity<DeleteApplicationResponseDTO> deleteApplicationByJobIdAndJobSeekerId(@RequestBody DeleteApplicationRequestDTO deleteApplicationRequestDTO) {
        return ResponseEntity.ok(applicationService.deleteApplicationByJobIdAndJobSeekerId(deleteApplicationRequestDTO));}

}
