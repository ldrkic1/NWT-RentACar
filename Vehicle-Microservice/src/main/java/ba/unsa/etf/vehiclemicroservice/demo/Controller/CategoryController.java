package ba.unsa.etf.vehiclemicroservice.demo.Controller;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Category;
import ba.unsa.etf.vehiclemicroservice.demo.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //http://localhost:8080/category/all
    @GetMapping(path="/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    //http://localhost:8080/category?id=1
    @GetMapping
    public Category getCategoryById(@RequestParam(value = "id") Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }
    @DeleteMapping("/{id}")
    public String deleteCategoryById(@PathVariable Long id){
         categoryService.deleteById(id);
         return "deleted Category by ID";
    }
    @PostMapping
    public Category createNewCategory(@RequestBody String description) {
        return categoryService.createNewCategory(description);
    }
    @GetMapping("/bydescription")
    public Category getCategoryByDescription(@RequestParam(value = "description") String description) {
        return categoryService.findCategoryByDescription(description);
    }
}
