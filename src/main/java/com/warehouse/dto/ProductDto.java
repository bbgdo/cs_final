package com.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotNull(message = "Product name cannot be null.")
    private String name;
    @Null
    private String description;
    @NotNull(message = "Product producer cannot be null.")
    private String producer;
    @NotNull
    @Min(value = 0, message = "Product amount cannot be null.")
    private int amount;
    @NotNull(message = "Product price cannot be null.")
    @Min(value = 0, message = "Product price cannot be less then 0.")
    private String price;
    @NotNull(message = "Product category cannot be null.")
    private String category;
}
