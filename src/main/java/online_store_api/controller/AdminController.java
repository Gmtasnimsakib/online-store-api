package online_store_api.controller;

import online_store_api.dto.ProductDTO;
import online_store_api.entities.Category;
import online_store_api.entities.Products;
import online_store_api.service.CategoryService;
import online_store_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

//    Category Section

    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String viewCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategories(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String saveCategories(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else
            return "404";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.removeCategory(id);
        return "redirect:/admin/categories";
    }

    //    Product Section
    @GetMapping("/admin/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.viewAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String addProducts(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productsAdd";
    }

    //    Upload Image Method
    @PostMapping("/admin/products/add")
    public String saveCategories(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {
        Products products = new Products();
        products.setId(productDTO.getId());
        products.setName(productDTO.getName());
        products.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        products.setPrice(productDTO.getPrice());
        products.setWeight(productDTO.getWeight());
        products.setDescription(productDTO.getDescription());
        products.setImageName(productDTO.getImageName());

        String imageUUID;
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else imageUUID = imgName;
        products.setImageName(imageUUID);
        productService.saveProducts(products);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id, Model model) {
        Products products = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();

        productDTO.setCategoryId(products.getCategory().getId());
        productDTO.setId(products.getId());
        productDTO.setName(products.getName());
        productDTO.setPrice(products.getPrice());
        productDTO.setWeight(products.getWeight());
        productDTO.setDescription(products.getDescription());
        productDTO.setImageName(products.getImageName());

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }
}