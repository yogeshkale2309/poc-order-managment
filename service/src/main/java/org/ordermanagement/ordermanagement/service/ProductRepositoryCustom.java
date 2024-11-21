package org.ordermanagement.ordermanagement.service;

import org.ordermanagement.ordermanagement.dto.ProductDTO;

import java.util.List;

public interface ProductRepositoryCustom {
    public List<ProductDTO> findWithShoppingCarts(Long customerId);
}
