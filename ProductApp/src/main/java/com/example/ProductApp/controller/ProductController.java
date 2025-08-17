package com.example.ProductApp.controller;

import com.example.ProductApp.dto.ProductDTO;
import com.example.ProductApp.mapper.ProductMapper;
import com.example.ProductApp.model.Product;
import com.example.ProductApp.service.Productservice;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Productservice productservice;

    public ProductController(Productservice productservice){
        this.productservice=productservice;
    }

    @PostMapping
    public String saveProduct(@Valid @RequestBody ProductDTO productDTO){
        Product product = ProductMapper.toEntity(productDTO);
        productservice.saveProduct(product);
        return " Product added";
    }

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts(){
        return productservice.findAllProduct().stream().map(ProductMapper::toProductDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getproductById(@PathVariable Long id){
        return ProductMapper.toProductDTO(productservice.findProductById(id));
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        Product p=productservice.findProductById(id);
        productservice.deleteProduct(p);
        return "Product deleted";
    }

    @PutMapping("/{id}")
    public String putProduct(@PathVariable Long id,@Valid @RequestBody ProductDTO productDTO){
        productservice.updateProduct(id,ProductMapper.toEntity(productDTO));
        return "Product updated";
    }
}
