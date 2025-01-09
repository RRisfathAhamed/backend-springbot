package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateJobSeekerRequestDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String dob;
    private String gender;

    private String education;
    private String experience;
    private String skills;
}
