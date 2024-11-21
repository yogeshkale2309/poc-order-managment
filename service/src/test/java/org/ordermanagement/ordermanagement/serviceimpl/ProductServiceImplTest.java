package org.ordermanagement.ordermanagement.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.ordermanagement.ordermanagement.dto.ProductDTO;
import org.ordermanagement.ordermanagement.entity.Product;
import org.ordermanagement.ordermanagement.entity.ShoppingCart;
import org.ordermanagement.ordermanagement.exception.ResourceNotFoundException;
import org.ordermanagement.ordermanagement.service.ProductRepositoryCustom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepositoryCustom mockProductRepository;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductsByCustomerId_SuccessfulScenario() {
        Long customerId = 123L;
        Product product = new Product();
        product.setId(1L);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        List<ProductDTO> products = new ArrayList<>();
        products.add(new ProductDTO(1L, "test", 10.0));
        when(mockProductRepository.findWithShoppingCarts(customerId)).thenReturn(products);

        List<ProductDTO> result = productService.getProductsByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockProductRepository).findWithShoppingCarts(customerId);
    }

    @Test
    void getProductsByCustomerId_EmptyResult() {
        Long customerId = 123L;
        List<ProductDTO> products = Arrays.asList();
        when(mockProductRepository.findWithShoppingCarts(customerId)).thenReturn(products);
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductsByCustomerId(customerId));
        verify(mockProductRepository).findWithShoppingCarts(customerId);
        verify(modelMapper, never()).map(any(), any());
    }
}
