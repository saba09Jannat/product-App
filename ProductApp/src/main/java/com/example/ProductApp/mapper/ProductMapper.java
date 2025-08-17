package com.example.ProductApp.mapper;

import com.example.ProductApp.dto.ProductDTO;
import com.example.ProductApp.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductDTO productDTO){
        Product p = new Product();
             p.setName(productDTO.getName());
             p.setPrice(productDTO.getPrice());
        return p;
    }

    public static ProductDTO toProductDTO(Product product){
        ProductDTO pd=new ProductDTO(product.getName(),product.getPrice());
        return pd;
    }
}
