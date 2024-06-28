package online_store_api.service;

import online_store_api.entities.Category;
import online_store_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void removeCategory(int id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
}
