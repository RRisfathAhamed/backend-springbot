package com.mora.jobrecommendationapp.DTO;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetJobProviderByIDResponseDTO {
    private String companyName;
    private String industry;
    private String email;
    private String phoneNumber;
    private String address;
    private Date registeredDate;
    private String userName;
}
