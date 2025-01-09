package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteApplicationRequestDTO {

    private Long jobSeekerId;
    private Long jobId;
}
