package com.mora.jobrecommendationapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CreateJobSeekerRequestDTO {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String dob;
    private String gender;
    private String registeredDate;
    private Boolean isCvUploaded;
    private String securityQuestion;
    private String securityAnswer;
}
