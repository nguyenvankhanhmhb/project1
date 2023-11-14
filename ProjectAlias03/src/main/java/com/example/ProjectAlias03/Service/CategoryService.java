package com.example.ProjectAlias03.Service;

import com.example.ProjectAlias03.Entity.CategoryEntity;
import com.example.ProjectAlias03.Repository.CategoryRepository;
import com.example.ProjectAlias03.Service.Imp.CategoryServiceImp;
import com.example.ProjectAlias03.payload.request.CategoryRequest;
import com.example.ProjectAlias03.payload.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<CategoryResponse> getAllCategory() {
        //Dữ liệu lấy được từ database
        List<CategoryEntity> list = categoryRepository.findAll();
        List<CategoryResponse> responseList = new ArrayList<>();
        for (CategoryEntity item : list) {
            //Duyệt qua từng dòng dữ liệu query được từ CategorEntity
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(item.getId_category());
            categoryResponse.setName(item.getName());

            responseList.add(categoryResponse);
        }

        return responseList;

    }

    @Override
    public CategoryEntity createCategory(CategoryRequest request) {
        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public CategoryEntity updateCategory(int id, CategoryRequest request) {
        try {
            Optional<CategoryEntity> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                CategoryEntity categoryEntity = category.get();
                categoryEntity.setName(request.getName());
                categoryRepository.save(categoryEntity);
                return categoryEntity;
            } else {
                System.out.println("Not Found Category With Id: " + id);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ ở đây
            System.out.println("Error occurred: " + e.getMessage());
            return null; // hoặc trả về một giá trị khác để biểu thị ngoại lệ
        }
        return null;
    }


    @Override
    public void deleteCategory(int id) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            CategoryEntity category = optionalCategory.get();
            categoryRepository.delete(category);
        } else {
            System.out.println("Not Found Category With Id: " + id);
        }
    }
    }
