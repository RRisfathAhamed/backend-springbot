package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJobSeekerByIDResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String dob;
    private String gender;
    private String registeredDate;
    private String userName;
    private String education;
    private String experience;
    private String skills;
    private Boolean isCvUploaded;
}
