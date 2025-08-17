package com.example.ProductApp.service;

import com.example.ProductApp.dto.ProductDTO;
import com.example.ProductApp.exception.ResourceNotFoundException;
import com.example.ProductApp.model.Product;
import com.example.ProductApp.repository.ProductRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Productservice {

    private final ProductRepository productRepository;

    public Productservice(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    @CacheEvict(value = "allProducts", allEntries = true)
    public void saveProduct(Product product){
        productRepository.save(product);
    }

    @Cacheable("allProducts")
    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Product findProductById(Long id){
        System.out.println("Fetching from db for id "+id);//will not print if cached
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product with id : "+id+" not found"));
    }

    // Delete product, evict both individual and list caches
    @Caching(evict = {
            @CacheEvict(value = "products", key = "#p.id"),
            @CacheEvict(value = "allProducts", allEntries = true)
    })
    public void deleteProduct(Product p){
        Long id = p.getId();
        Product product = productRepository.findById(id)
                        .orElseThrow(()->new ResourceNotFoundException("Product not found"));
        productRepository.delete(p);
    }

    // Update product, evict both caches
    @Caching(evict = {
            @CacheEvict(value = "products", key = "#id"),
            @CacheEvict(value = "allProducts", allEntries = true)
    })
    public void updateProduct(Long id, Product product){
       Product existingProduct=productRepository.findById(id)
                       .orElseThrow(()->new ResourceNotFoundException("Product with id : "+id+" does not exist"));
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            productRepository.save(existingProduct);  // <-- this saves the update


    }
}
