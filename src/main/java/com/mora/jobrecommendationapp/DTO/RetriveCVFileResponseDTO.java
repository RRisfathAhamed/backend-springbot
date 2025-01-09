package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RetriveCVFileResponseDTO {

    private byte[] cvFile;
    private String fileName;
}
