package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteJobSeekerRequestDTO {
    private Boolean isDeleted;
}
