package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgetPasswordResponseDTO {

    private String message;

    private Boolean isEmailSent;

}
