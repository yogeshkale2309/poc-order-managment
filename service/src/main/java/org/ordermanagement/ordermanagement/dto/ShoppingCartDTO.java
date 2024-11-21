package org.ordermanagement.ordermanagement.dto;



import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private List<ProductDTO> products;

    public ShoppingCartDTO() {}

    public ShoppingCartDTO(List<ProductDTO> products) {
        this.products = products != null ? products : new ArrayList<>();
    }

    public List<ProductDTO> getProducts() {
        return products != null ? products : new ArrayList<>();
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products != null ? products : new ArrayList<>();
    }
}
