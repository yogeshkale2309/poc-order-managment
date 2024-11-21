package org.ordermanagement.ordermanagement;


import org.ordermanagement.ordermanagement.dto.CustomerDTO;
import org.ordermanagement.ordermanagement.dto.ShoppingCartDTO;
import org.ordermanagement.ordermanagement.exception.ResourceNotFoundException;
import org.ordermanagement.ordermanagement.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {
    private final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/cart/{customerId}")
    public ResponseEntity<CustomerDTO> getCartDetails(@PathVariable Long customerId) {
        try {
            CustomerDTO cartDetails = shoppingCartService.getCartDetails(customerId);
            return ResponseEntity.ok(cartDetails);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Shopping cart not created by: " + customerId);
        }
    }
    @PostMapping("/{customerId}/add/{productId}")
    public ResponseEntity<ShoppingCartDTO> addToCart(@PathVariable Long customerId, @PathVariable Long productId) {
        ShoppingCartDTO result = shoppingCartService.addToCart(customerId, productId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerId}/remove/{productId}")
    public ResponseEntity<ShoppingCartDTO> removeFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        ShoppingCartDTO result = shoppingCartService.removeFromCart(customerId, productId);
        return ResponseEntity.ok(result);
    }

}
