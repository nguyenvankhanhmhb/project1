package com.example.ProjectAlias03.payload.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductRequest {

//    @NotNull(message = "File không được phép rỗng" )
    private MultipartFile file;
    @NotBlank(message = "Tên không được rỗng")
    private String name;
    private String description;

    private double discount;
//    @DecimalMin(value="0.1")
    private double price;
    private int quantity;
    private int sizeId;
    private int colorId;
    private int categoryId;
    private List<String> ImageId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public List<String> getImageId() {
        return ImageId;
    }

    public void setImageId(List<String> imageId) {
        ImageId = imageId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
