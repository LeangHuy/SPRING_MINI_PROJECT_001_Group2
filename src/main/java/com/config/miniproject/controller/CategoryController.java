package com.config.miniproject.controller;

import com.config.miniproject.utils.ApiResponse;
import com.config.miniproject.model.dto.request.CategoryRequest;
import com.config.miniproject.model.dto.response.CategoryResponse;
import com.config.miniproject.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/author/category")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.createCategory(categoryRequest);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .status(HttpStatus.CREATED)
                .message("A new category is created successfully.")
                .payload(response)
                .timestamp(LocalDateTime.now())
                .statusCode(201)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderBy
    ) {
        List<CategoryResponse> response = categoryService.getAllCategory(orderBy, sortBy, page, size);
        ApiResponse<List<CategoryResponse>> apiResponse = ApiResponse.<List<CategoryResponse>>builder()
                .status(HttpStatus.OK)
                .message("Get category by id successfully")
                .payload(response)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get category by its id")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable @Positive Integer id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .status(HttpStatus.OK)
                .message("get category by id successfully.")
                .payload(response)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category by id")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable @Positive Integer id,
            @Valid
            @RequestBody CategoryRequest categoryRequest) {

        CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryRequest);

        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .status(HttpStatus.OK)
                .message("Category updated successfully.")
                .payload(updatedCategory)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by its id")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable("id") @Positive Integer id) {
        categoryService.deleteCategory(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Category with id " + id + " is deleted successfully.")
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
