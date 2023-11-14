package com.example.ProjectAlias03.Service;

import com.example.ProjectAlias03.Entity.*;
import com.example.ProjectAlias03.Repository.CategoryRepository;
import com.example.ProjectAlias03.Repository.ImageRepository;
import com.example.ProjectAlias03.Repository.ProductRepository;
import com.example.ProjectAlias03.Service.Imp.ProductServiceImp;
import com.example.ProjectAlias03.payload.request.ProductRequest;
import com.example.ProjectAlias03.payload.response.ProductResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private ProductRepository productRepository;
    @Value("${host.name}")
    private String hostName;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

//    private Gson gson =new Gson();

    // chứa tất cả các sản phẩm có trong cơ sở dữ liệu.
    @Override
    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity createProduct(ProductRequest request) {

//
//        Type userListType = new TypeToken<ArrayList<ProductRequest>>(){}.getType();
//        ArrayList<ProductRequest> usersList = new Gson().fromJson(jsonArray, userListType);



        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(request.getCategoryId());
        CategoryEntity category = optionalCategory.orElse(new CategoryEntity()); // Hoặc thực hiện xử lý nếu không tìm thấy Category
        product.setCategory(category);


        List<ImageEntity> image = new ArrayList<>();
        for (String imageId : request.getImageId()) {
            Optional<ImageEntity> optionalImage = imageRepository.findById(imageId);
            if (optionalImage.isPresent()) {
                ImageEntity imageEntity = optionalImage.get();
                image.add(imageEntity);
            } else {
                throw new RuntimeException("Không tìm thấy ảnh với ID: " + imageId);
            }
        }
        product.setImage(product.getImage());// filed image là danh sach(ProductEntity)
        productRepository.save(product);
        return product;
    }

//    @Override
//    public ProductEntity updateProduct(int id, ProductRequest request) {
//        Optional<ProductEntity> product = productRepository.findById(id);
//        if(product.isPresent()){
//            System.out.println("Not Found Product With Id: " + id);
//            return null; // hoặc thực hiện hành động khác, không ném ngoại lệ
//        }
//
//        ProductEntity productEntity = product.get();
//
//        productEntity.setName(request.getName());
//        productEntity.setDescription(request.getDescription());;
//        productEntity.setPrice(request.getPrice());
//        productEntity.setQuantity(request.getQuantity());
//
//
//        Optional<CategoryEntity> category = categoryRepository.findById(request.getCategoryId());
//        if(category.isEmpty()){
//            System.out.println("Not Found Category With Id: " + id);
//            return null; // hoặc thực hiện hành động khác, không ném ngoại lệ
//        }
//        CategoryEntity categoryEntity = category.get();
//        productEntity.setCategory(categoryEntity);
//
//        List<ImageEntity> images = new ArrayList<>();
//        for (String imageId : request.getImageId()) {
//            Optional<ImageEntity> optionalImage = imageRepository.findById(imageId);
//            if (optionalImage.isPresent()) {
//                ImageEntity image = optionalImage.get();
//                images.add(image);
//            } else {
//                throw new RuntimeException("Không tìm thấy ảnh với ID: " + imageId);
//            }
//        }
//        productEntity.setImage(productEntity.getImage());//filed image là danh sach(ImageEntity)
//        productRepository.setImages(i);
//
//        return productEntity;
//    }

    @Override
    @Cacheable("getProductByCategory")
    public List<ProductResponse> getProductByCategoryId(String hostName, int id) {
        System.out.println("kiem tra");
        List<ProductEntity> list = productRepository.findByCategory(id);
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (ProductEntity data: list){
            ProductResponse productResponse =new ProductResponse();
            productResponse.setId(data.getId());
            productResponse.setName(data.getName());
            productResponse.setPrice(data.getPrice());

            Optional image = imageRepository.findById(data.getImage().getId());
            if(image != null){
                productResponse.setImage("http://" + hostName + "/product/file/" + image.get());
            }
            productResponseList.add(productResponse);

        }
        return productResponseList;
    }

    @Override
    public boolean addProduct(ProductRequest productResquest) {
        //nd cần thêm
        boolean isSuccess = false;
        try{
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productResquest.getName());
            productEntity.setImage(productEntity.getImage());
            productEntity.setPrice(productResquest.getPrice());
            productEntity.setQuantity(productEntity.getQuantity());

            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setId(productResquest.getColorId());

            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setId(productResquest.getSizeId());

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(productResquest.getCategoryId());

            productEntity.setColor(colorEntity);
            productEntity.setSize(sizeEntity);
            productEntity.setCategory(categoryEntity);

            productRepository.save(productEntity);
            isSuccess= true;


        }catch (Exception e){
            System.out.println("Error  add product: " +e.getLocalizedMessage());

        }
        return isSuccess;
    }

    @Override
    public ProductResponse getDetailProduct(int id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        ProductResponse productResponse = new ProductResponse();
        if(product.isPresent()){
            productResponse.setId(product.get().getId());

            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setId(productResponse.getId());
            productResponse.setImage(productResponse.getImage());

            productResponse.setName(product.get().getName());
            productResponse.setPrice(product.get().getPrice());
            productResponse.setDesc(product.get().getDescription());

        }
        return productResponse;
    }

    @Override
    @CacheEvict(value = "getProductByCategory", allEntries = true)
    public boolean clearCache() {
        return true;

    }

//    @Override
//    public List<ProductEntity> getListByPrice() {
//        return productRepository.getListByPrice();
//    }

//    @Override
//    public List<ProductEntity> findRelatedProduct(long id) {
//        List<ProductEntity> list = productRepository.findRelatedProduct(id);
//        return list;
//
//    }
@Override
public List<ProductEntity> searchProducts(String keyword) {
    return productRepository.findByNameContaining(keyword);
}

//    @Override
//    public List<ProductEntity> searchProduct(String keyword) {
//        List<ProductEntity> list = productRepository.searchProduct(keyword);
//        return list;
//    }

    @Override
    public void deleteProductById(int id) {
        this.productRepository.deleteById(id);

    }

    @Override
    public boolean saveProduct(ProductRequest product) {
        try {
            // Tạo một đối tượng ProductEntity từ dữ liệu trong ProductRequest
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(product.getName());
            productEntity.setPrice(product.getPrice());
            productEntity.setQuantity(product.getQuantity());
            // Các bước khác...

            // Lưu trữ ProductEntity đã được tạo
            productRepository.save(productEntity);
            return true;
        } catch (Exception e) {
            return false;
        }

}

    @Override
    public ProductEntity getProductById(int id) {
        Optional<ProductEntity> optional=productRepository.findById(id);
        ProductEntity product=null;
        if(optional.isPresent()) {
            product=optional.get();
        }else {
            throw new RuntimeException("Product not found for id::"+id);
        }
        return product;
    }
}
