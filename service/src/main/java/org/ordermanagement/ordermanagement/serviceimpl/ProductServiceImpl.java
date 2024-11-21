package org.ordermanagement.ordermanagement.serviceimpl;

import org.modelmapper.ModelMapper;
import org.ordermanagement.ordermanagement.dto.ProductDTO;
import org.ordermanagement.ordermanagement.exception.ResourceNotFoundException;
import org.ordermanagement.ordermanagement.service.ProductRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);



    @Autowired
    private  ProductRepositoryCustom productRepository;
    @Autowired
    private  ModelMapper modelMapper;



    public List<ProductDTO> getProductsByCustomerId(Long customerId) {
        log.info("Fetching products for customer: {}", customerId);
        List<ProductDTO> products = productRepository.findWithShoppingCarts(customerId);
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("Products not found for customer: " + customerId);
        }
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductDTO toDto(ProductDTO product) {
        return modelMapper.map(product, ProductDTO.class);
    }

}
