package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CreateJobProviderRequestDTO {

    private String companyName;
    private String industry;
    private String email;
    private String userName;
    private String password;
    private String phoneNumber;
    private String address;
    private Date registeredDate;
    private String securityQuestion;
    private String securityAnswer;
}
