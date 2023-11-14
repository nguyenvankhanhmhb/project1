package com.example.ProjectAlias03.Service.Imp;

import com.example.ProjectAlias03.Entity.CategoryEntity;
import com.example.ProjectAlias03.payload.request.CategoryRequest;
import com.example.ProjectAlias03.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryServiceImp {
    List<CategoryResponse> getAllCategory();

    CategoryEntity createCategory(CategoryRequest request);

    CategoryEntity updateCategory(int id,CategoryRequest request);

    void deleteCategory(int id);
}
