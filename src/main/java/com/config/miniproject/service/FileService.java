package com.config.miniproject.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String saveFile(MultipartFile file) throws IOException;

    Resource findFile(String filename) throws IOException;
}
