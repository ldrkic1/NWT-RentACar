package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Category;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.CategoryRepository;
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

    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()) {
            return category.get();
        }
        else throw new NotFoundException("Category with id: " + categoryId + " doesn't exist.");

    }

    public Category findCategoryByDescription(String description) {
        try {
            Category category = categoryRepository.findByDescription(description);
            System.out.println("BANANA");
            return categoryRepository.findByDescription(description);
        }catch (Exception e) {
            throw new NotFoundException("Category with the name: "+description+" doesn't exist. ");
        }
    }
    public void deleteById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()) {
            categoryRepository.deleteById(categoryId);
        }
        else throw new NotFoundException("Category with id: " + categoryId + " doesn't exist.");
    }
    public Category createNewCategory(String description) {
        Category category = new Category();
        category.setDescription(description);
        return categoryRepository.save(category);
    }
    //sve kategorije

}
