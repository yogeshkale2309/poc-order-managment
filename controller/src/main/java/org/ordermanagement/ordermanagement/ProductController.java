package org.ordermanagement.ordermanagement;


import org.ordermanagement.ordermanagement.dto.ProductDTO;
import org.ordermanagement.ordermanagement.serviceimpl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products/customer")
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCustomerId(@PathVariable Long customerId) {
        log.info("Get products by customer id {} ", customerId);
        List<ProductDTO> productDtos = productService.getProductsByCustomerId(customerId);
        return ResponseEntity.ok(productDtos);
    }
}
