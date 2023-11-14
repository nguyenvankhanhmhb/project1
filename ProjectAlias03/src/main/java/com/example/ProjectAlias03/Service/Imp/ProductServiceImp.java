package com.example.ProjectAlias03.Service.Imp;

import com.example.ProjectAlias03.Entity.ProductEntity;
import com.example.ProjectAlias03.payload.request.ProductRequest;
import com.example.ProjectAlias03.payload.response.ProductResponse;

import java.util.List;

public interface ProductServiceImp {

    List<ProductEntity> getAllProduct();


    ProductEntity createProduct(ProductRequest request);
//    ProductEntity updateProduct(int id, ProductRequest request);

    List<ProductResponse> getProductByCategoryId(String hostName, int id);
//    boolean addProduct(ProductRequest productResquest);

    ProductResponse getDetailProduct(int id);

    boolean clearCache();

    List<ProductEntity> searchProducts(String keyword);
//    List<ProductEntity> getListByPrice();

//    List<ProductEntity> findRelatedProduct(long id);

//    List<ProductEntity> searchProduct(String keyword);

    void deleteProductById(int id);
    boolean saveProduct(ProductRequest product);
    ProductEntity getProductById(int id);
    boolean addProduct(ProductRequest productRequest);

}
