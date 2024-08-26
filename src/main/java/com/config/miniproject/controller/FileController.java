package com.config.miniproject.controller;


import com.config.miniproject.model.dto.response.FileResponse;
import com.config.miniproject.service.FileService;
import com.config.miniproject.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
@Builder
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Upload file")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String filename = fileService.saveFile(file);
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(filename).toUriString();
        FileResponse fileResponse = new FileResponse(filename,
                fileUrl
                , file.getContentType()
                , file.getSize());
        ApiResponse<FileResponse> apiResponse = ApiResponse.<FileResponse>builder()
                .message("File uploaded successfully")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(fileResponse)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }




    @Operation(summary = "Get all files")
    @GetMapping("file")
    public ResponseEntity<?> getFile(@RequestParam String filename) throws IOException {
        Resource resource = fileService.findFile(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
    }
}
