package com.mora.jobrecommendationapp.services;

import com.mora.jobrecommendationapp.DTO.*;
import com.mora.jobrecommendationapp.entities.Job;
import com.mora.jobrecommendationapp.entities.JobProvider;
import com.mora.jobrecommendationapp.repositories.JobProviderRepository;
import com.mora.jobrecommendationapp.repositories.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobProviderRepository jobProviderRepository;
    @Autowired
    private JobSeekerService jobSeekerService;

    public CreateJobResponseDTO createJob( CreateJobRequestDTO createJobRequestDTO) {
        Optional<JobProvider> jobProvider = Optional.of(new JobProvider());
        jobProvider= jobProviderRepository.findById(createJobRequestDTO.getJobProviderId());
        Job job = Job.builder()
                .jobTitle(createJobRequestDTO.getJobTitle())
                .jobDescription(createJobRequestDTO.getJobDescription())
                .jobLocation(createJobRequestDTO.getJobLocation())
                .jobExperience(createJobRequestDTO.getJobExperience())
                .qualifiedEducation(createJobRequestDTO.getQualifiedEducation())
                .jobSkills(createJobRequestDTO.getJobSkills())
                .jobPostedDate(createJobRequestDTO.getJobPostedDate())
                .jobProvider(jobProvider.get())
                .build();
        jobRepository.save(job);
        CreateJobResponseDTO createJobResponseDTO = CreateJobResponseDTO.builder()
                .message(job.getJobTitle()+" Job Created Successfully with "+ job.getJobSkills())
                .build();
        return createJobResponseDTO;
    }

    public UpdateJobResponseDTO updateJob(long id, UpdateJobRequestDTO updateJobRequestDTO) throws ChangeSetPersister.NotFoundException {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        job.setJobTitle(updateJobRequestDTO.getJobTitle());
        job.setJobDescription(updateJobRequestDTO.getJobDescription());
        job.setJobLocation(updateJobRequestDTO.getJobLocation());
        job.setJobExperience(updateJobRequestDTO.getJobExperience());
        job.setQualifiedEducation(updateJobRequestDTO.getQualifiedEducation());
        job.setJobSkills(updateJobRequestDTO.getJobSkills());
        job.setIsHired(updateJobRequestDTO.getIsHired());
        jobRepository.save(job);
        UpdateJobResponseDTO updateJobResponseDTO = UpdateJobResponseDTO.builder().
                message("Job Seeker Updated Successfully for the user name ")
                .build();
        return updateJobResponseDTO;
    }

    @Transactional
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private JobDTO mapToDTO(Job job) {
        return JobDTO.builder()
                .jobId(job.getJobId())
                .jobTitle(job.getJobTitle())
                .jobDescription(job.getJobDescription())
                .jobLocation(job.getJobLocation())
                .jobExperience(job.getJobExperience())
                .qualifiedEducation(job.getQualifiedEducation())
                .jobSkills(job.getJobSkills())
                .jobPostedDate(job.getJobPostedDate())
                .isHired(job.getIsHired())
                .build();
    }

    public JobDTO getJobById(long id) {
        Optional<Job> job = jobRepository.findById(id);
        return mapToDTO(job.orElse(null));
    }

    public DeleteJobResponseDTO deleteJob(long id) {
        jobRepository.deleteById(id);
        DeleteJobResponseDTO deleteJobResponseDTO = DeleteJobResponseDTO.builder()
                .message("Job Deleted Successfully")
                .build();
        return deleteJobResponseDTO;
    }

    public long getJobCount() {
        return jobRepository.countJobs();
    }

    // Find jobs by skills
    public Map<String, List<JobMatchDTO>> findJobsBySkills(Long userId) {
        GetJobSeekerSkillsResponseDTO jobSeekerSkills = jobSeekerService.getJobSeekerSkillsById(userId);
        List<String> userSkills = Arrays.stream(jobSeekerSkills.getSkills().split(","))
                .map(skill -> skill.trim().toLowerCase().replaceAll("\\s+", " "))
                .collect(Collectors.toList());

        List<Job> allJobs = jobRepository.findAll();
        List<JobMatchDTO> matchedJobs = new ArrayList<>();

        for (Job job : allJobs) {
            List<String> jobSkills = Arrays.stream(job.getJobSkills().split(","))
                    .map(skill -> skill.trim().toLowerCase().replaceAll("\\s+", " "))
                    .collect(Collectors.toList());

            long matchedSkillsCount = userSkills.stream()
                    .filter(jobSkills::contains)
                    .count();

            double matchPercentage = Math.round(((double) matchedSkillsCount / jobSkills.size()) * 100 / 10.0) * 10.0;

            if (matchPercentage >= 50) {
                String comment = generateMatchComment(matchPercentage);

                matchedJobs.add(
                        JobMatchDTO.builder()
                                .jobId(job.getJobId())
                                .jobTitle(job.getJobTitle())
                                .jobDescription(job.getJobDescription())
                                .jobLocation(job.getJobLocation())
                                .jobExperience(job.getJobExperience())
                                .qualifiedEducation(job.getQualifiedEducation())
                                .jobSkills(job.getJobSkills())
                                .jobPostedDate(job.getJobPostedDate())
                                .isHired(job.getIsHired())
                                .matchPercentage(matchPercentage)
                                .comment(comment)
                                .build()
                );
            }
        }

        // Sort jobs by match percentage in descending order
        matchedJobs.sort((a, b) -> Double.compare(b.getMatchPercentage(), a.getMatchPercentage()));

        // Group jobs based on their match percentage
        Map<String, List<JobMatchDTO>> groupedJobs = matchedJobs.stream()
                .collect(Collectors.groupingBy(job -> {
                    if (job.getMatchPercentage() == 100) {
                        return "Highly recommend";
                    } else if (job.getMatchPercentage() >= 80) {
                        return "Strongly recommend";
                    } else if (job.getMatchPercentage() >= 60) {
                        return "Recommend";
                    } else {
                        return "Considerable";
                    }
                }));

        return groupedJobs;
    }


    // Find jobs by job title
    public List<JobDTO> findJobsByJobTitle(String jobTitle) {
        String normalizedJobTitle = jobTitle.trim().toLowerCase().replaceAll("\\s+", " ");

        List<Job> allJobs = jobRepository.findAll();
        List<JobDTO> matchedJobs = new ArrayList<>();

        for (Job job : allJobs) {
            String normalizedJobTitleFromDb = job.getJobTitle().trim().toLowerCase().replaceAll("\\s+", " ");

            if (normalizedJobTitleFromDb.contains(normalizedJobTitle)) {
                matchedJobs.add(
                        JobDTO.builder()
                                .jobId(job.getJobId())
                                .jobTitle(job.getJobTitle())
                                .jobDescription(job.getJobDescription())
                                .jobLocation(job.getJobLocation())
                                .jobExperience(job.getJobExperience())
                                .qualifiedEducation(job.getQualifiedEducation())
                                .jobSkills(job.getJobSkills())
                                .jobPostedDate(job.getJobPostedDate())
                                .isHired(job.getIsHired())
                                .build()
                );
            }
        }

        return matchedJobs;
    }

    private String generateMatchComment(double matchPercentage) {
        if (matchPercentage == 100) {
            return "Highly recommend.";
        } else if (matchPercentage >= 80) {
            return "Strongly recommend.";
        } else if (matchPercentage >= 60) {
            return "Recommend.";
        } else if (matchPercentage >= 50) {
            return "Considerable.";
        } else {
            return "Not recommend.";
        }
    }



}

//    public Job createJob(Job job) {
//        // Check if jobProviderId is provided
////        if (job.getJobProviderId() != null) {
////            JobProvider jobProvider = jobProviderRepository.findById(job.getJobProviderId())
////                    .orElseThrow(() -> new RuntimeException("JobProvider not found"));
////            job.setJobProvider(jobProvider);
////        }
//        job.setJobPostedDate(new Date());
//        return jobRepository.save(job);
//    }
//}
