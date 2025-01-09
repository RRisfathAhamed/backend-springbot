package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateApplicationResponseDTO {
    private String message;
    private Boolean isDuplicated;
}
