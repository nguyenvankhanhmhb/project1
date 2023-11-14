package com.example.ProjectAlias03.Repository;


import com.example.ProjectAlias03.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
}
