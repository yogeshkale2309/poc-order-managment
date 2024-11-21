package org.ordermanagement.ordermanagement.serviceimpl;

import org.ordermanagement.ordermanagement.dto.CustomerDTO;
import org.ordermanagement.ordermanagement.dto.ShoppingCartDTO;
import org.modelmapper.ModelMapper;
import org.ordermanagement.ordermanagement.entity.Customer;
import org.ordermanagement.ordermanagement.entity.Product;
import org.ordermanagement.ordermanagement.entity.ShoppingCart;
import org.ordermanagement.ordermanagement.exception.ResourceNotFoundException;
import org.ordermanagement.ordermanagement.repos.CustomerRepository;
import org.ordermanagement.ordermanagement.repos.ProductRepository;
import org.ordermanagement.ordermanagement.repos.ShoppingCartRepository;
import org.ordermanagement.ordermanagement.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository cartRepository, ProductRepository productRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO getCartDetails(Long customerId) {
        log.debug("Fetching cart details for customer ID: {}", customerId);

        if (customerId == null || customerId <= 0) {
            log.warn("Invalid customer ID: {}", customerId);
            return null;
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            log.error("Customer not found for ID: {}", customerId);
            throw new ResourceNotFoundException("Customer not found");
        }
        Customer customer = optionalCustomer.get();
        log.debug("Mapped customer to DTO: {}", customer.getName());
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public ShoppingCartDTO addToCart(Long customerId, Long productId) {
        log.info("Adding to cart: " + customerId);
        try {
            ShoppingCart cart = cartRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for customer: " + customerId));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

            boolean productExistsInCart = cart.getProducts().stream()
                    .anyMatch(p -> p.getId().equals(productId));

            if (productExistsInCart) {
                return modelMapper.map(cart, ShoppingCartDTO.class);
            }

            cart.getProducts().add(product);
            cartRepository.save(cart);
            log.info("Product added to cart: " + productId);
            return modelMapper.map(cart, ShoppingCartDTO.class);
        } catch (ResourceNotFoundException e) {
            log.error("Error adding to cart", e);
            throw e;
        }
    }

    @Override
    public ShoppingCartDTO removeFromCart(Long customerId, Long productId) {
        log.info("Removing from cart: " + customerId);
        try {
            ShoppingCart cart = cartRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for customer: " + customerId));

            boolean productExistsInCart = cart.getProducts() != null && cart.getProducts().stream()
                    .anyMatch(p -> p.getId().equals(productId));


            if (!productExistsInCart) {
                throw new ResourceNotFoundException("Product not found in cart");
            }


            cart.getProducts().removeIf(p -> p.getId().equals(productId));
            cartRepository.save(cart);
            log.info("Product removed from cart: " + productId);
            return modelMapper.map(cart, ShoppingCartDTO.class);
        } catch (ResourceNotFoundException e) {
            log.error("Error removing from cart", e);
            throw e;
        }

    }
}

