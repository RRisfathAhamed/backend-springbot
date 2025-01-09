package com.mora.jobrecommendationapp.services;

import com.mora.jobrecommendationapp.DTO.*;
import com.mora.jobrecommendationapp.JwtAuthenticationConfig.JWTAuthentication;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import com.mora.jobrecommendationapp.entities.PasswordResetToken;
import com.mora.jobrecommendationapp.repositories.JobSeekerRepository;
import com.mora.jobrecommendationapp.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service

public class JobSeekerService {
    @Autowired
    JobSeekerRepository jobSeekerRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTAuthentication jwtA;

    @Autowired
    public JobSeekerService(JobSeekerRepository jobSeekerRepository , BCryptPasswordEncoder bCryptPasswordEncoder, JWTAuthentication jwtA) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtA = jwtA;
    }

    public CreateJobSeekerResponseDTO createJobSeeker(CreateJobSeekerRequestDTO jobSeekerRequest) {
        String encryptedPassword = bCryptPasswordEncoder.encode(jobSeekerRequest.getPassword());
        String securityAnswerHash = bCryptPasswordEncoder.encode(jobSeekerRequest.getSecurityAnswer());

        JobSeeker existingJobSeeker = jobSeekerRepository.findByUserName(jobSeekerRequest.getUserName());
        if (existingJobSeeker == null) {
            JobSeeker jobSeeker = JobSeeker.builder()
                    .firstName(jobSeekerRequest.getFirstName())
                    .lastName(jobSeekerRequest.getLastName())
                    .userName(jobSeekerRequest.getUserName())
                    .email(jobSeekerRequest.getEmail())
                    .password(encryptedPassword)
                    .phoneNumber(jobSeekerRequest.getPhoneNumber())
                    .address(jobSeekerRequest.getAddress())
                    .dob(jobSeekerRequest.getDob())
                    .gender(jobSeekerRequest.getGender())
                    .isCvUploaded(false)
                    .registeredDate(jobSeekerRequest.getRegisteredDate())
                    .securityQuestion(jobSeekerRequest.getSecurityQuestion())
                    .securityAnswerHash(securityAnswerHash)
                    .build();
            jobSeekerRepository.save(jobSeeker);
            CreateJobSeekerResponseDTO responseDTO = CreateJobSeekerResponseDTO.builder()
                    .message("Job Seeker Created Successfully for the user name " + jobSeekerRequest.getUserName())
                    .isDuplicated(false)
                    .build();
            return responseDTO;
        } else {
            CreateJobSeekerResponseDTO responseDTO = CreateJobSeekerResponseDTO.builder()
                    .message("Job Seeker Already Exists for the user name " + jobSeekerRequest.getUserName())
                    .isDuplicated(true)
                    .build();
            return responseDTO;
        }
    }

    public LoginResponse performlogin(LoginJobSeekerRequestDTO loginJobSeekerRequestDTO) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUserName(loginJobSeekerRequestDTO.getUserName());
        String userName = loginJobSeekerRequestDTO.getUserName();
        String password = loginJobSeekerRequestDTO.getPassword();

        if (jobSeeker == null) {
            return new LoginResponse("User not found", false,null);
        } else {
            boolean isPasswordMatched = bCryptPasswordEncoder.matches(password, jobSeeker.getPassword());
            if (isPasswordMatched) {
                String token= jwtA.generateToken(userName);
                return new LoginResponse("Login Successful", true,token);
            } else {
                return new LoginResponse("Incorrect Password", false,null);
            }
        }
    }

    public UpdateJobSeekerResponseDTO updateJobSeeker(long id, UpdateJobSeekerRequestDTO updateJobSeekerRequest) {

        JobSeeker jobSeeker = jobSeekerRepository.findById(id).get();;
        if (jobSeeker != null) {
            jobSeeker.setFirstName(updateJobSeekerRequest.getFirstName());
            jobSeeker.setLastName(updateJobSeekerRequest.getLastName());
            jobSeeker.setPhoneNumber(updateJobSeekerRequest.getPhoneNumber());
            jobSeeker.setAddress(updateJobSeekerRequest.getAddress());
            jobSeeker.setDob(updateJobSeekerRequest.getDob());
            jobSeeker.setGender(updateJobSeekerRequest.getGender());
            jobSeeker.setEducation(updateJobSeekerRequest.getEducation());
            jobSeeker.setExperience(updateJobSeekerRequest.getExperience());
            jobSeeker.setSkills(updateJobSeekerRequest.getSkills());
            jobSeekerRepository.save(jobSeeker);
            UpdateJobSeekerResponseDTO updateJobSeekerResponseDTO = UpdateJobSeekerResponseDTO.builder().
                    message("Job Seeker Updated Successfully for the user name "+jobSeeker.getUserName())
                    .build();
            return updateJobSeekerResponseDTO;
        }

        UpdateJobSeekerResponseDTO updateJobSeekerResponseDTO = UpdateJobSeekerResponseDTO.builder().
                message("Job Seeker Not Updated ")
                .build();
        return updateJobSeekerResponseDTO;    }


    // Update your service method to fetch the JobSeeker entity eagerly
    public GetJobSeekerByIDResponseDTO getJobSeekerById(Long id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id).get();
        GetJobSeekerByIDResponseDTO getJobSeekerByIDResponseDTO = GetJobSeekerByIDResponseDTO.builder()
                .firstName(jobSeeker.getFirstName())
                .lastName(jobSeeker.getLastName())
                .email(jobSeeker.getEmail())
                .phoneNumber(jobSeeker.getPhoneNumber())
                .address(jobSeeker.getAddress())
                .dob(jobSeeker.getDob())
                .gender(jobSeeker.getGender())
                .registeredDate(jobSeeker.getRegisteredDate())
                .userName(jobSeeker.getUserName())
                .education(jobSeeker.getEducation())
                .experience(jobSeeker.getExperience())
                .skills(jobSeeker.getSkills())
                .isCvUploaded(jobSeeker.getIsCvUploaded())
                .build();
        return getJobSeekerByIDResponseDTO;
    }

    public DeleteJobSeekerResponseDTO deleteJobSeeker(long id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id).get();
        if (jobSeeker != null) {
            jobSeekerRepository.delete(jobSeeker);
            DeleteJobSeekerResponseDTO deleteJobSeekerResponseDTO = DeleteJobSeekerResponseDTO.builder().
                    message("Job Seeker Deleted Successfully ")
                    .build();
            return deleteJobSeekerResponseDTO;
        }
        DeleteJobSeekerResponseDTO deleteJobSeekerResponseDTO = DeleteJobSeekerResponseDTO.builder().
                message("Job Seeker Not Deleted")
                .build();
        return deleteJobSeekerResponseDTO;
    }

    public UploadCVResponseDTO updateCV(long id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id).get();
        if (jobSeeker != null) {
            jobSeeker.setIsCvUploaded(true);
            jobSeekerRepository.save(jobSeeker);
            UploadCVResponseDTO uploadCVResponseDTO = UploadCVResponseDTO.builder().
                    message("CV Updated Successfully")
                    .build();
            return uploadCVResponseDTO;
        }
        UploadCVResponseDTO uploadCVResponseDTO = UploadCVResponseDTO.builder().
                message("CV Not Updated")
                .build();
        return uploadCVResponseDTO;
    }

    public UpdateJobSeekerSkillsResponseDTO updateSkills(long id, UpdateJobSeekerSkillsRequestDTO updateJobSeekerSkillsRequest) {

        JobSeeker jobSeeker = jobSeekerRepository.findById(id).get();
        if (jobSeeker != null) {
            jobSeeker.setSkills(updateJobSeekerSkillsRequest.getSkills());
            jobSeeker.setExperience(updateJobSeekerSkillsRequest.getExperience());
            jobSeeker.setEducation(updateJobSeekerSkillsRequest.getEducation());
            jobSeekerRepository.save(jobSeeker);
            UpdateJobSeekerSkillsResponseDTO updateJobSeekerSkillsResponseDTO = UpdateJobSeekerSkillsResponseDTO.builder().
                    message("Skills Updated Successfully")
                    .build();
            return updateJobSeekerSkillsResponseDTO;
        }
        UpdateJobSeekerSkillsResponseDTO updateJobSeekerSkillsResponseDTO = UpdateJobSeekerSkillsResponseDTO.builder().
                message("Skills Not Updated")
                .build();
        return updateJobSeekerSkillsResponseDTO;
    }

    public GetJobSeekerSkillsResponseDTO getJobSeekerSkillsById(Long id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id).get();
        GetJobSeekerSkillsResponseDTO getJobSeekerSkillsResponseDTO = GetJobSeekerSkillsResponseDTO.builder()
                .id(jobSeeker.getJobSeekerId())
                .skills(jobSeeker.getSkills())
                .build();
        return getJobSeekerSkillsResponseDTO;
    }

//    public ForgetPasswordResponseDTO forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) {
//        JobSeeker jobSeeker = jobSeekerRepository.findByEmail(forgetPasswordRequestDTO.getEmail());
//        if (jobSeeker != null) {
//            String token = UUID.randomUUID().toString();
//            PasswordResetToken resetToken = new PasswordResetToken(token, jobSeeker);
//            tokenRepository.save(resetToken);
//            emailService.sendResetLink(email, token);
//
//            ForgetPasswordResponseDTO forgetPasswordResponseDTO = ForgetPasswordResponseDTO.builder().
//                    message("Password Reset Link Sent Successfully")
//                    .build();
//            return forgetPasswordResponseDTO;
//        }
//
//    }

    public void forgotPassword(String email) {

        JobSeeker jobSeeker = jobSeekerRepository.findByEmail(email);

        if (jobSeeker != null) {
            String token = UUID.randomUUID().toString();
            Optional<PasswordResetToken> existingTokenOpt = tokenRepository.findByJobSeeker(jobSeeker);

            PasswordResetToken resetToken;
            if (existingTokenOpt.isPresent()) {
                // Update the existing token
                resetToken = existingTokenOpt.get();
                resetToken.setToken(token);
                resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Set a new expiry date if needed
            } else {
                // Create a new token
                resetToken = new PasswordResetToken(token, jobSeeker);
            }

            tokenRepository.save(resetToken);
            emailService.sendResetLink(email, token);
        }
    }


    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken != null && !resetToken.isExpired()) {
            JobSeeker jobSeeker = resetToken.getJobSeeker();
            jobSeeker.setPassword(passwordEncoder.encode(newPassword));
            jobSeekerRepository.save(jobSeeker);
            tokenRepository.delete(resetToken);
        } else {
            throw new RuntimeException("Invalid or expired token.");
        }
    }

    public JobSeeker getJobSeekerByUserName(String userName) {
        return jobSeekerRepository.findByUserName(userName);
    }

    public UploadCVResponseDTO uploadCV(long id, MultipartFile file) {
        Optional<JobSeeker> jobSeekerOptional = jobSeekerRepository.findById(id);
        if (jobSeekerOptional.isPresent()) {
            JobSeeker jobSeeker = jobSeekerOptional.get();
            try {
                jobSeeker.setCvFile(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            jobSeeker.setCvFileType(file.getContentType());
            jobSeeker.setCvFileName(file.getOriginalFilename());
            jobSeeker.setIsCvUploaded(true);
            jobSeekerRepository.save(jobSeeker);

            UploadCVResponseDTO uploadCVResponseDTO = UploadCVResponseDTO.builder().
                    message("CV Uploaded Successfully")
                    .build();
            return uploadCVResponseDTO;
        } else {
            throw new RuntimeException("Job Seeker not found");
        }

    }

    public RetriveCVFileResponseDTO getCV(long id) {
        Optional<JobSeeker> jobSeekerOptional = jobSeekerRepository.findById(id);
        if (jobSeekerOptional.isPresent()) {
            JobSeeker jobSeeker = jobSeekerOptional.get();

            RetriveCVFileResponseDTO retriveCVFileResponseDTO = RetriveCVFileResponseDTO.builder().
                    cvFile(jobSeeker.getCvFile())
                    .fileName(jobSeeker.getCvFileName())
                    .build();
            return retriveCVFileResponseDTO;

        } else {
            throw new RuntimeException("Job Seeker not found");
        }
    }

    public Object deleteCV(long id) {
        Optional<JobSeeker> jobSeekerOptional = jobSeekerRepository.findById(id);
        if (jobSeekerOptional.isPresent()) {
            JobSeeker jobSeeker = jobSeekerOptional.get();
            jobSeeker.setCvFile(null);
            jobSeeker.setCvFileName(null);
            jobSeeker.setCvFileType(null);
            jobSeeker.setIsCvUploaded(false);
            jobSeekerRepository.save(jobSeeker);
            DeleteCVResponseDTO deleteCVResponseDTO = DeleteCVResponseDTO.builder().
                    message("CV Deleted Successfully")
                    .build();
            return deleteCVResponseDTO;
        } else {
            throw new RuntimeException("Job Seeker not found");
        }
    }
    public GetSecurityQuestionResponseDTO getSecurityQuestion(GetSecurityQuestionRequestDTO requestDTO) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUserName(requestDTO.getUserName());
        GetSecurityQuestionResponseDTO responseDTO = new GetSecurityQuestionResponseDTO();
        if (jobSeeker != null) {
            responseDTO.setSuccess(true);
            responseDTO.setSecurityQuestion(jobSeeker.getSecurityQuestion());
        } else {
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    public ValidateSecurityAnswerResponseDTO validateSecurityAnswer(ValidateSecurityAnswerRequestDTO requestDTO) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUserName(requestDTO.getUserName());
        ValidateSecurityAnswerResponseDTO responseDTO = new ValidateSecurityAnswerResponseDTO();
        if (jobSeeker != null) {
            boolean isAnswerMatched = bCryptPasswordEncoder.matches(
                    requestDTO.getSecurityAnswer(), jobSeeker.getSecurityAnswerHash());
            responseDTO.setSuccess(isAnswerMatched);
        } else {
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
}
