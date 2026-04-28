package com.example.productmanagement.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product{
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 5, max = 150, message = "Độ dài từ 5 đến 150 ký tự")
    private String productName;

    @NotBlank(message = "Vui lòng chọn danh mục")
    private String category; // Điện tử, Thời trang, Gia dụng

    @NotNull(message = "Giá không được để trống")
    @Min(value = 1, message = "Giá phải lớn hơn 0")
    @Max(value = 100000000, message = "Giá không quá 100.000.000 VNĐ")
    private double price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải là số nguyên không âm")
    private Integer stock;

    @Size(max = 500, message = "Mô tả tối đa 500 ký tự")
    private String description;
}