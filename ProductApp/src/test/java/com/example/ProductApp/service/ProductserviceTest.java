package com.example.ProductApp.service;

import com.example.ProductApp.exception.ResourceNotFoundException;
import com.example.ProductApp.model.Product;
import com.example.ProductApp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductserviceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private Productservice productservice;

    private Product product;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);

    }

    @Test
    void testSaveProduct() {

        when(productRepository.save(product)).thenReturn(product);

        productservice.saveProduct(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testFindAllProduct() {
        List<Product> products = List.of(new Product(1L, "P1", 10.0), new Product(2L, "P2", 20.0));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productservice.findAllProduct();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void testFindProductById_Found() {

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productservice.findProductById(1L);

        assertEquals("Test Product", result.getName());
    }

    @Test
    void testFindProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productservice.findProductById(1L));
    }

    @Test
    void testDeleteProduct() {
         when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productservice.deleteProduct(product);

        verify(productRepository).delete(product);
    }

    @Test
    void testUpdateProduct() {
        Product existing = product;
        Product updated = new Product(1L, "New", 75.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(updated);

        productservice.updateProduct(1L, updated);

        assertEquals("New", existing.getName());
        assertEquals(75.0, existing.getPrice());
    }

    @Test
    void findProductById_shouldThrowExceptionWhenNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productservice.findProductById(1L));
    }
    @Test
    void deleteProduct_shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productservice.deleteProduct(product));
    }
    @Test
    void updateProduct_shouldThrowExceptionWhenProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Name");
        updatedProduct.setPrice(200.0);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productservice.updateProduct(1L, updatedProduct));
    }




}
