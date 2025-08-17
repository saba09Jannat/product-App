package com.example.ProductApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {


    @NotBlank(message="Product name cannot be blank")
    @Size(min=10,max=50,message="Product name should be between 10 and 50 characters")
    private String name;
    @Positive(message="Product price must be positive ")
    @Min(value=1000,message="Price should be atleast 1000")
    private Double price;

    public ProductDTO(String name, Double price){

        this.name=name;
        this.price=price;
    }

    public ProductDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
