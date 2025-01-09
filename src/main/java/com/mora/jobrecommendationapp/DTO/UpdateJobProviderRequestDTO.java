package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class UpdateJobProviderRequestDTO {
    private String companyName;
    private String industry;
    private String email;
    private String phoneNumber;
    private String address;
}
