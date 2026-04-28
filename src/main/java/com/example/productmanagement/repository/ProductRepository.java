package com.example.productmanagement.repository;

import com.example.productmanagement.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository{
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> findAll(){
        return products;
    }


    public void save(Product product){
        if (product.getId() == null){
            product.setId(idGenerator.getAndIncrement());
            products.add(product);
        }else{
            //Update logic
            int index = -1;
            for (int i = 0; i < products.size(); i++){
                if(products.get(i).getId().equals(product.getId())){
                    index = i;
                    break;
                }
            }
            if (index != -1) products.set(index, product);
        }
    }

    public Product findById(Long id){
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public void delete(Long id){
        products.removeIf(p -> p.getId().equals(id));
    }
    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }
    public ProductRepository() {
        // Thêm dữ liệu mẫu để test hiển thị ngay khi chạy
        save(new Product(null, "Laptop Dell XPS", "Điện tử", 250000, 10, "Máy đẹp"));
        save(new Product(null, "Áo sơ mi nam", "Thời trang", 500000, 50, "Vải lụa"));
    }
}