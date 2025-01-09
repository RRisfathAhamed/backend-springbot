package com.mora.jobrecommendationapp.controllers;

import com.mora.jobrecommendationapp.DTO.*;
import com.mora.jobrecommendationapp.entities.Job;
import com.mora.jobrecommendationapp.entities.JobProvider;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import com.mora.jobrecommendationapp.services.JobProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/jobprovider")
public class JobProviderController {
    @Autowired
    private JobProviderService jobProviderService;

    @PostMapping("/create")
    public ResponseEntity<CreateJobProviderResponseDTO> createJobProvider(@RequestBody CreateJobProviderRequestDTO jobProviderRequest) {
        return ResponseEntity.ok(jobProviderService.createJobProvider(jobProviderRequest));
    }

    @GetMapping("/getAllJobProvider")
    public List<JobProvider> getAllJobProvider() {
        return jobProviderService.getAllJobProvider();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginJobProviderRequestDTO loginJobProviderRequestDTO) {
        return ResponseEntity.ok(jobProviderService.performlogin(loginJobProviderRequestDTO));
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<UpdateJobProviderResponseDTO> updateJobProvider(@PathVariable("id") long id,@RequestBody UpdateJobProviderRequestDTO updateJobProviderRequest) {
        return ResponseEntity.ok(jobProviderService.updateJobProvider(id,updateJobProviderRequest));
    }

    @GetMapping("/{id}/jobs")
    public ResponseEntity<List<Job>> getJobsByJobProviderId(@PathVariable("id") Long jobProviderId) {
        List<Job> jobs = jobProviderService.getJobsByJobProviderId(jobProviderId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}/jobcount")
    public ResponseEntity<Long> getJobCountByJobProviderId(@PathVariable("id") Long jobProviderId) {
        Long jobCount = jobProviderService.getJobCountByJobProviderId(jobProviderId);
        return ResponseEntity.ok(jobCount);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteJobSeekerResponseDTO> deleteJobProviderById(@PathVariable("id") Long jobProviderId) {
        return ResponseEntity.ok(jobProviderService.deleteJobProviderById(jobProviderId));
    }

    @GetMapping("/get/{id}")
    public GetJobProviderByIDResponseDTO getJobProviderById(@PathVariable Long id) {
        return jobProviderService.getJobProviderById1(id);
    }

    @PostMapping("/get-security-question")
    public ResponseEntity<GetSecurityQuestionResponseDTO> getSecurityQuestion(@RequestBody GetSecurityQuestionRequestDTO requestDTO) {
        return ResponseEntity.ok(jobProviderService.getSecurityQuestion(requestDTO));
    }

    @PostMapping("/validate-security-answer")
    public ResponseEntity<ValidateSecurityAnswerResponseDTO> validateSecurityAnswer(@RequestBody ValidateSecurityAnswerRequestDTO requestDTO) {
        return ResponseEntity.ok(jobProviderService.validateSecurityAnswer(requestDTO));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO) {
        JobProvider jobProvider = jobProviderService.getJobProviderByUserName(forgetPasswordRequestDTO.getUserName());

        if (jobProvider != null) {
            jobProviderService.forgotPassword(jobProvider.getEmail());
            return ResponseEntity.ok("Password reset link sent to your email.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        try {
            jobProviderService.resetPassword(resetPasswordRequestDTO.getToken(), resetPasswordRequestDTO.getPassword());
            return ResponseEntity.ok("Password has been reset.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
