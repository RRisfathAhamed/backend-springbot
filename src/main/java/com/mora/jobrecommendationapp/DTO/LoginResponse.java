package com.mora.jobrecommendationapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class LoginResponse {
    private String message;
    private boolean isLoginSuccess;
    private String token;
}

