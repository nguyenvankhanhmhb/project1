package com.example.ProjectAlias03.Controller;


import com.example.ProjectAlias03.Entity.CategoryEntity;
import com.example.ProjectAlias03.Service.CategoryService;
import com.example.ProjectAlias03.Service.Imp.CategoryServiceImp;
import com.example.ProjectAlias03.payload.BaseResponse;
import com.example.ProjectAlias03.payload.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        BaseResponse response = new BaseResponse();
        response.setData(categoryServiceImp.getAllCategory());
        return new ResponseEntity<>("get all product", HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest request){
        CategoryEntity category = categoryServiceImp.createCategory(request);

        return new ResponseEntity<>("Tạo Thành Công",HttpStatus.OK);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @Valid @RequestBody
                                                     CategoryRequest request){
        CategoryEntity category = categoryServiceImp.updateCategory(id, request);
        return new ResponseEntity<>("Cập Nhật thành công",HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        categoryServiceImp.deleteCategory(id);
        return new ResponseEntity<>("Xoá thành công",HttpStatus.OK);
    }

}
