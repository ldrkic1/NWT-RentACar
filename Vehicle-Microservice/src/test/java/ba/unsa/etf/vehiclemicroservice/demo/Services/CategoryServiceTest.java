package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.ApiRequestException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.ValidationException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    @Test
    public void deleteCategoryById() {
        assertAll(
                () -> assertThrows(
                        NotFoundException.class,
                        () -> categoryService.deleteById(15L)),
                () -> assertEquals(3, categoryService.getAllCategories().size())
        );
    }
    @Test
    public void createNewCategory() {
        String desctription = "Teretno";
        categoryService.createNewCategory(desctription);
        assertEquals(4, categoryService.getAllCategories().size());
    }
    @Test
    public void findCategoryByDescription() {
        Category category = categoryService.findCategoryByDescription("Putnicka");
        assertEquals(1,category.getId());
    }
    @Test
    public void getCategoryById() {
        Category category = categoryService.getCategoryById(2L);
        assertEquals("Transportna", category.getDescription());
    }
}
