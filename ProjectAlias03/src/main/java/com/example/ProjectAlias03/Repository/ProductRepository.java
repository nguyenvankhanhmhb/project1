package com.example.ProjectAlias03.Repository;

import com.example.ProjectAlias03.Entity.ImageEntity;
import com.example.ProjectAlias03.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

//    List<ProductEntity> getListByPrice();

//    @Query(value ="Select * from Product where category_id = :id order by rand() limit 4",nativeQuery = true)
//
//    List<ProductEntity> findRelatedProduct(long id);

    List<ProductEntity> getListProductByCategory(long id);

//   List<ProductEntity> searchProduct(String name);

    List<ProductEntity> findByCategory(int id);
    List<ProductEntity> findByNameContaining(String keyword);

}
