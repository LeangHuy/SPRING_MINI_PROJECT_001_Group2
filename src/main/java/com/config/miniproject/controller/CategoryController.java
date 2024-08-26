package com.config.miniproject.controller;

import com.config.miniproject.model.dto.ApiDeleteResponse;
import com.config.miniproject.model.dto.ApiResponse;
import com.config.miniproject.model.dto.request.CategoryRequest;
import com.config.miniproject.model.dto.response.CategoryResponse;
import com.config.miniproject.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author/category")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.createCategory(categoryRequest);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .status(HttpStatus.CREATED)
                .message("A new category is created successfully.")
                .payload(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "categoryId") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderBy
    ) {
        List<CategoryResponse> response = categoryService.getAllCategory(orderBy, sortBy, page, size);
        ApiResponse<List<CategoryResponse>> apiResponse = ApiResponse.<List<CategoryResponse>>builder()
                .status(HttpStatus.OK)
                .message("Get category by id successfully")
                .payload(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Integer id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .status(HttpStatus.OK)
                .message("get category by id successfully.")
                .payload(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryRequest categoryRequest) {

        CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryRequest);

        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .status(HttpStatus.OK)
                .message("Category updated successfully.")
                .payload(updatedCategory)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDeleteResponse<String>> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        ApiDeleteResponse<String> apiResponse = ApiDeleteResponse.<String>builder()
                .message("Category with id " + id + " is deleted successfully.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
