package com.example.ProjectAlias03.Entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private int code;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "discount")
    private double discount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_image")
    private ImageEntity image;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private SizeEntity size;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "id_review")
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private ColorEntity color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageEntity getImage() {
        return image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }

    public SizeEntity getSize() {
        return size;
    }

    public void setSize(SizeEntity size) {
        this.size = size;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public ReviewEntity getReview() {
        return review;
    }

    public void setReview(ReviewEntity review) {
        this.review = review;
    }

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
    }


}
