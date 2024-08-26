package com.config.miniproject.controller;

import com.config.miniproject.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
}
