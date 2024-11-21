package org.ordermanagement.ordermanagement.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.ordermanagement.ordermanagement.dto.CustomerDTO;
import org.ordermanagement.ordermanagement.dto.ShoppingCartDTO;
import org.ordermanagement.ordermanagement.entity.Customer;
import org.ordermanagement.ordermanagement.entity.Product;
import org.ordermanagement.ordermanagement.entity.ShoppingCart;
import org.ordermanagement.ordermanagement.exception.ResourceNotFoundException;
import org.ordermanagement.ordermanagement.repos.CustomerRepository;
import org.ordermanagement.ordermanagement.repos.ProductRepository;
import org.ordermanagement.ordermanagement.repos.ShoppingCartRepository;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ShoppingCartRepository cartRepository;

    @InjectMocks
    private ShoppingCartServiceImpl serviceImpl;

    private ShoppingCart cart;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        serviceImpl=new ShoppingCartServiceImpl(cartRepository,productRepository,customerRepository,modelMapper);
        cart = new ShoppingCart();

    }

    @Test
    void getCartDetails_ShouldReturnCustomerDTO_WhenCustomerExists() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));

        assertDoesNotThrow(() -> serviceImpl.getCartDetails(customerId));

        CustomerDTO result = serviceImpl.getCartDetails(customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals(customer.getName(), result.getName());
    }


    @Test
    void getCartDetails_ShouldThrowResourceNotFoundException_WhenCustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> serviceImpl.getCartDetails(customerId));
    }

    @Test
    void addToCart_ShouldAddProductToCart_WhenProductDoesNotExistInCart() {
        Long customerId = 1L;
        Long productId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setCustomer(customer);
        Product product = new Product();
        product.setId(productId);
        product.setName("Product");
        List<Product> products = new ArrayList<>();
        products.add(product);
        cart.setProducts(products);
        when(cartRepository.findById(customerId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ShoppingCartDTO result = serviceImpl.addToCart(customerId, productId);
        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        assertEquals(productId, result.getProducts().get(0).getId());
        assertEquals(product.getName(), result.getProducts().get(0).getName());
    }

    @Test
    void removeFromCart_ShouldRemoveProductFromCart_WhenProductExistsInCart() {
        Long customerId = 1L;
        Long productId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        ShoppingCart cart = new ShoppingCart();
        cart.setCustomer(customer);
        Product product = new Product();
        product.setId(productId);
        product.setName("Product");
        List<Product> products = new ArrayList<>();
        products.add(product);
        cart.setProducts(products);

        when(cartRepository.findById(customerId)).thenReturn(java.util.Optional.of(cart));

        ShoppingCartDTO result = serviceImpl.removeFromCart(customerId, productId);

        assertTrue(result.getProducts().isEmpty());
    }
    @Test
    void getCartDetails_ShouldReturnNull_WhenCustomerIdIsNullOrLessThanOrEqualToZero() {
        Long customerId = null;
        assertNull(serviceImpl.getCartDetails(customerId));

        customerId = 0L;
        assertNull(serviceImpl.getCartDetails(customerId));

        customerId = -1L;
        assertNull(serviceImpl.getCartDetails(customerId));
    }
    @Test
    void addToCart_ShouldThrowResourceNotFoundException_WhenAddingToCartWithNonExistingCustomer() {
        Long nonExistingCustomerId = 100L;
        Long existingProductId = 1L;

        when(cartRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> serviceImpl.addToCart(nonExistingCustomerId, existingProductId));
    }
    @Test
    void addToCart_ShouldThrowResourceNotFoundException_WhenAddingToCartWithNonExistingProduct() {
        Long existingCustomerId = 1L;
        Long nonExistingProductId = 100L;

        when(cartRepository.findById(existingCustomerId)).thenReturn(Optional.of(new ShoppingCart()));
        when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> serviceImpl.addToCart(existingCustomerId, nonExistingProductId));
    }
    @Test
    void addToCart_ShouldReturnSameCart_WhenAddingProductThatAlreadyExistsInCart() {
        Long customerId = 1L;
        Long productId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setCustomer(customer);
        Product product = new Product();
        product.setId(productId);
        product.setName("Product");
        List<Product> products = new ArrayList<>();
        products.add(product);
        cart.setProducts(products);
        when(cartRepository.findById(customerId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ShoppingCartDTO result = serviceImpl.addToCart(customerId, productId);
        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        assertEquals(productId, result.getProducts().get(0).getId());
        assertEquals(product.getName(), result.getProducts().get(0).getName());
    }
    @Test
    public void testAddToCart() {
        Long customerId = 1L;
        Long productId = 2L;
        ShoppingCart cart = new ShoppingCart();
        cart.setId(customerId);
        cart.setProducts(new ArrayList<>());
        Product product = new Product();
        product.setId(productId);
        when(cartRepository.findById(customerId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ShoppingCartDTO result = serviceImpl.addToCart(customerId, productId);
        verify(cartRepository).save(any(ShoppingCart.class));
        assertEquals(productId, result.getProducts().get(0).getId());
        assertEquals(product.getName(), result.getProducts().get(0).getName());
    }
    @Test
    void removeFromCart_ShouldThrowResourceNotFoundException_WhenRemovingFromCartWithNonExistingCustomer() {
        Long nonExistingCustomerId = 100L;
        Long existingProductId = 1L;

        when(cartRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> serviceImpl.removeFromCart(nonExistingCustomerId, existingProductId));
    }

    @Test
    void removeFromCart_whenProductNotInCart_shouldThrowResourceNotFoundException() throws Exception {
        Long customerId = 1L;
        Long productId = 2L;
        ShoppingCart cart = new ShoppingCart();
        when(cartRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> serviceImpl.removeFromCart(customerId, productId));
    }
    @Test
    public void testRemoveFromCart_ProductNotFound() {
        Long customerId = 1L;
        Long productId = 99L;

        when(cartRepository.findById(customerId)).thenReturn(Optional.of(cart));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            serviceImpl.removeFromCart(customerId, productId);
        });

        assertEquals("Product not found in cart", thrown.getMessage());
    }
}
