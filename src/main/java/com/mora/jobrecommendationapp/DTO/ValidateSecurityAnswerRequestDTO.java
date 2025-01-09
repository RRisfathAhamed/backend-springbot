package com.mora.jobrecommendationapp.DTO;

import lombok.Data;

@Data
public class ValidateSecurityAnswerRequestDTO {
    private String userName;
    private String securityAnswer;
}