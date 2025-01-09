package com.mora.jobrecommendationapp.services;

import com.mora.jobrecommendationapp.DTO.CreateApplicationRequestDTO;
import com.mora.jobrecommendationapp.DTO.CreateApplicationResponseDTO;
import com.mora.jobrecommendationapp.DTO.DeleteApplicationRequestDTO;
import com.mora.jobrecommendationapp.DTO.DeleteApplicationResponseDTO;
import com.mora.jobrecommendationapp.entities.Application;
import com.mora.jobrecommendationapp.entities.Job;
import com.mora.jobrecommendationapp.entities.JobProvider;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import com.mora.jobrecommendationapp.repositories.ApplicationRepository;
import com.mora.jobrecommendationapp.repositories.JobProviderRepository;
import com.mora.jobrecommendationapp.repositories.JobRepository;
import com.mora.jobrecommendationapp.repositories.JobSeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    JobSeekerRepository jobSeekerRepository;
    @Autowired
    JobRepository jobRepository;

    public CreateApplicationResponseDTO createApplication(CreateApplicationRequestDTO createApplicationRequestDTO) {
        Optional<JobSeeker> jobSeeker = Optional.of(new JobSeeker());
        jobSeeker= jobSeekerRepository.findById(createApplicationRequestDTO.getJobSeekerId());

        Optional<Job> job = Optional.of(new Job());
        job= jobRepository.findById(createApplicationRequestDTO.getJobId());

        boolean isDuplicateFound = applicationRepository.findByJobSeekerIdAndJobId(createApplicationRequestDTO.getJobSeekerId(),createApplicationRequestDTO.getJobId());
        if (isDuplicateFound) {
            return CreateApplicationResponseDTO.builder()
                    .message("Application Already Exists")
                    .isDuplicated(true)
                    .build();
        }
        else {
        Application application = Application.builder()
                .jobAppliedDate(createApplicationRequestDTO.getJobAppliedDate())
                .applicationStatus(createApplicationRequestDTO.getApplicationStatus())
                .jobSeeker(jobSeeker.get())
                .job(job.get())
                .build();
        applicationRepository.save(application);
        return CreateApplicationResponseDTO.builder()
                .message("Application Created Successfully")
                .isDuplicated(false)
                .build();
        }
    }

    public long getApplicationCountByJobId(Long jobId) {
        return applicationRepository.countByJobId(jobId);
    }

    public long getApplicationCountByJobSeekerId(Long jobSeekerId) {
        return applicationRepository.countByJobSeekerId(jobSeekerId);
    }

    public List<Job> getJobsByJobSeekerId(Long jobSeekerId) {
        return applicationRepository.findJobsByJobSeekerId(jobSeekerId);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public List<JobSeeker> getApplicationsByJobId(Long jobId) {
        return applicationRepository.findApplicationsByJobId(jobId);
    }

    public DeleteApplicationResponseDTO deleteApplicationById(Long applicationId) {
        applicationRepository.deleteById(applicationId);
        return DeleteApplicationResponseDTO.builder()
                .message("Application Deleted Successfully")
                .isDeleted(true)
                .build();
    }

    public DeleteApplicationResponseDTO deleteApplicationByJobIdAndJobSeekerId(DeleteApplicationRequestDTO deleteApplicationRequestDTO) {
        applicationRepository.deleteByJobSeekerIdAndJobId(deleteApplicationRequestDTO.getJobSeekerId(),deleteApplicationRequestDTO.getJobId());
        return DeleteApplicationResponseDTO.builder()
                .message("Application Deleted Successfully")
                .isDeleted(true)
                .build();
    }
}
