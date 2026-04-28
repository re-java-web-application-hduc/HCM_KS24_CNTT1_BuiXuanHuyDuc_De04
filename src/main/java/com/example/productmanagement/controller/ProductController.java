package com.example.productmanagement.controller;

import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController{

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public String listProducts(Model model,
                               @RequestParam(name = "keyword", required = false) String keyword,
                               @RequestParam(name = "sortBy", defaultValue = "productName") String sortBy,
                               @RequestParam(name = "dir", defaultValue = "asc") String dir) {

        List<Product> list = repository.findAll();

        //Search
        if (keyword != null && !keyword.trim().isEmpty()){
            list = list.stream()
                    .filter(p -> p.getProductName().toLowerCase().contains(keyword.toLowerCase())
                            || p.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        //sort
        Comparator<Product> comparator = switch (sortBy){
            case "price" -> Comparator.comparing(Product::getPrice);
            case "stock" -> Comparator.comparing(Product::getStock);
            default -> Comparator.comparing(Product::getProductName);
        };

        if ("desc".equalsIgnoreCase(dir)){
            comparator = comparator.reversed();
        }
        list.sort(comparator);

        //Đổ dữ liệu ra Model
        model.addAttribute("products", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);

        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product", new Product());
        return "form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") Product product, BindingResult result){
        if (result.hasErrors()){
            return "form";
        }
        repository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model){
        model.addAttribute("product", repository.findById(id));
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id){
        repository.delete(id);
        return "redirect:/products";
    }
}