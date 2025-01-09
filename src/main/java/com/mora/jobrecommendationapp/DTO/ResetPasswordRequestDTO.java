package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequestDTO {

    private String token;
    private String password;
}
