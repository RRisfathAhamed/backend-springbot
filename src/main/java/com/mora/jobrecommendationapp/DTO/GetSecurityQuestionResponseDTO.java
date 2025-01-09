package com.mora.jobrecommendationapp.DTO;

import lombok.Data;

@Data
public class GetSecurityQuestionResponseDTO {
    private boolean success;
    private String securityQuestion;
}