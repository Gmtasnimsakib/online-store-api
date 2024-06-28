package online_store_api.service;

import online_store_api.entities.Products;
import online_store_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Products> viewAllProducts() {
        return productRepository.findAll();
    }

    public void saveProducts(Products products) {
        productRepository.save(products);
    }

    public Optional<Products> getProductById(long id) {
        return productRepository.findById(id);
    }

    public List<Products> getAllProductsByCategoryId(int id) {
        return productRepository.findAllByCategory_Id(id);
    }

    public void removeProductById(long id) {
        productRepository.deleteById(id);
    }
}
