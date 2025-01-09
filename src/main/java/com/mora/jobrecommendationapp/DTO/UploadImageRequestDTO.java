package com.mora.jobrecommendationapp.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadImageRequestDTO {

    private MultipartFile file;


}
