package com.example.ProductApp.mapper;

import com.example.ProductApp.dto.ProductDTO;
import com.example.ProductApp.model.Product;
import com.example.ProductApp.mapper.ProductMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    class ProductMapperTest {

        @Test
        void testToEntity() {
            // Arrange
            ProductDTO dto = new ProductDTO("Test Product", 199.99);

            // Act
            Product product = ProductMapper.toEntity(dto);

            // Assert
            assertNotNull(product);
            assertEquals(dto.getName(), product.getName());
            assertEquals(dto.getPrice(), product.getPrice());
        }

        @Test
        void testToProductDTO() {
            // Arrange
            Product product = new Product();
            product.setName("Sample Product");
            product.setPrice(499.50);

            // Act
            ProductDTO dto = ProductMapper.toProductDTO(product);

            // Assert
            assertNotNull(dto);
            assertEquals(product.getName(), dto.getName());
            assertEquals(product.getPrice(), dto.getPrice());
        }

        @Test
        void testToEntityWithNullValues() {
            // Arrange
            ProductDTO dto = new ProductDTO(null, null);

            // Act
            Product product = ProductMapper.toEntity(dto);

            // Assert
            assertNotNull(product);
            assertNull(product.getName());
            assertNull(product.getPrice());
        }

        @Test
        void testToProductDTOWithNullValues() {
            // Arrange
            Product product = new Product();
            product.setName(null);
            product.setPrice(null);

            // Act
            ProductDTO dto = ProductMapper.toProductDTO(product);

            // Assert
            assertNotNull(dto);
            assertNull(dto.getName());
            assertNull(dto.getPrice());
        }
    }



