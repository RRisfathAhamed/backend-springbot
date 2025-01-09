package com.mora.jobrecommendationapp.controllers;

import com.mora.jobrecommendationapp.DTO.*;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import com.mora.jobrecommendationapp.services.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/jobseeker")

public class JobSeekerController {
    @Autowired
    private JobSeekerService jobSeekerService;
    @PostMapping("/create")
    public ResponseEntity<CreateJobSeekerResponseDTO> createJobSeeker(@RequestBody CreateJobSeekerRequestDTO jobSeekerRequest) {
        return ResponseEntity.ok(jobSeekerService.createJobSeeker(jobSeekerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginJobSeekerRequestDTO loginJobSeekerRequestDTO) {
        return ResponseEntity.ok(jobSeekerService.performlogin(loginJobSeekerRequestDTO));
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<UpdateJobSeekerResponseDTO> updateJobSeeker(@PathVariable("id") long id,@RequestBody UpdateJobSeekerRequestDTO updateJobSeekerRequest) {
        return ResponseEntity.ok(jobSeekerService.updateJobSeeker(id,updateJobSeekerRequest));
    }

    @GetMapping("/get/{id}")
    public GetJobSeekerByIDResponseDTO getJobSeekerById(@PathVariable Long id) {
        return jobSeekerService.getJobSeekerById(id);
    }

    @GetMapping("/getSkills/{id}")
    public GetJobSeekerSkillsResponseDTO getJobSeekerSkillsById(@PathVariable Long id) {
        return jobSeekerService.getJobSeekerSkillsById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteJobSeekerResponseDTO> deleteJobSeeker(@PathVariable("id") long id) {
        return ResponseEntity.ok(jobSeekerService.deleteJobSeeker(id));
    }

    @PutMapping("/update_cv/{id}")
    public ResponseEntity<UploadCVResponseDTO> updateCV(@PathVariable("id") long id) {
        return ResponseEntity.ok(jobSeekerService.updateCV(id));
    }

    @PutMapping("/update_skills/{id}")
    public ResponseEntity<UpdateJobSeekerSkillsResponseDTO> updateSkills(@PathVariable("id") long id,@RequestBody UpdateJobSeekerSkillsRequestDTO updateJobSeekerSkillsRequest) {
        return ResponseEntity.ok(jobSeekerService.updateSkills(id,updateJobSeekerSkillsRequest));
    }

//    @PostMapping("/forgetPassword")
//    public ResponseEntity<ForgetPasswordResponseDTO> forgetPassword(@RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO) {
//        return ResponseEntity.ok(jobSeekerService.forgetPassword(forgetPasswordRequestDTO));
//    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO) {
        JobSeeker jobSeeker = jobSeekerService.getJobSeekerByUserName(forgetPasswordRequestDTO.getUserName());

        if (jobSeeker != null) {
            jobSeekerService.forgotPassword(jobSeeker.getEmail());
            return ResponseEntity.ok("Password reset link sent to your email.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        jobSeekerService.resetPassword(resetPasswordRequestDTO.getToken(), resetPasswordRequestDTO.getPassword());
        return ResponseEntity.ok("Password has been reset.");
    }

    @PostMapping("/upload-cv-img/{id}")
    public ResponseEntity<UploadCVResponseDTO> uploadCV(@PathVariable ("id") long id, @RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(jobSeekerService.uploadCV(id, file));

    }

    @GetMapping("/get-cv/{id}")
    public ResponseEntity<RetriveCVFileResponseDTO> getCV(@PathVariable("id") long id) {
        return ResponseEntity.ok(jobSeekerService.getCV(id));
    }

    @DeleteMapping("/delete-cv/{id}")
    public ResponseEntity<DeleteCVResponseDTO> deleteCV(@PathVariable("id") long id) {
        return ResponseEntity.ok((DeleteCVResponseDTO) jobSeekerService.deleteCV(id));
    }

    @PostMapping("/get-security-question")
    public ResponseEntity<GetSecurityQuestionResponseDTO> getSecurityQuestion(@RequestBody GetSecurityQuestionRequestDTO requestDTO) {
        return ResponseEntity.ok(jobSeekerService.getSecurityQuestion(requestDTO));
    }

    @PostMapping("/validate-security-answer")
    public ResponseEntity<ValidateSecurityAnswerResponseDTO> validateSecurityAnswer(@RequestBody ValidateSecurityAnswerRequestDTO requestDTO) {
        return ResponseEntity.ok(jobSeekerService.validateSecurityAnswer(requestDTO));
    }


}
