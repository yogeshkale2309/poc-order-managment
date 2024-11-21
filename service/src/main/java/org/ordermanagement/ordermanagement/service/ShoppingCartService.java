package org.ordermanagement.ordermanagement.service;

import org.ordermanagement.ordermanagement.dto.CustomerDTO;
import org.ordermanagement.ordermanagement.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    public CustomerDTO getCartDetails(Long customerId);

    ShoppingCartDTO addToCart(Long customerId, Long productId);

    ShoppingCartDTO removeFromCart(Long customerId, Long productId);
}
