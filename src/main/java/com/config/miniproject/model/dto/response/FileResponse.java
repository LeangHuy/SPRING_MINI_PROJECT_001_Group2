package com.config.miniproject.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
}
