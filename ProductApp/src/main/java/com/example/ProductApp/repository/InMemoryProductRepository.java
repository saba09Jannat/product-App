

package com.example.ProductApp.repository;

import com.example.ProductApp.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository {
/**
    List<Product> products = new ArrayList<>();

    public void save(Product product){
        products.add(product);
    }

    public List<Product> findAll(){
        return products;
    }

    public Optional<Product> findById(long id){
        return products.stream().filter(p->p.getId().id).findFirst();
    }

    public void delete(long id){
        products.removeIf(p->p.getId().id);
    }

    public void update(String id , Product Updateproduct){
        Optional<Product> optionalProduct=findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(Updateproduct.getName());
            product.setPrice(Updateproduct.getPrice());
        }
    }
 **/
}

