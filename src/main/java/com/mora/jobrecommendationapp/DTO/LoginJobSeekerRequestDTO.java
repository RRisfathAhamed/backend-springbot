package com.mora.jobrecommendationapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LoginJobSeekerRequestDTO {

    private String userName;
    private String password;
}
