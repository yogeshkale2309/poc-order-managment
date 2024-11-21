package org.ordermanagement.ordermanagement;


import org.ordermanagement.ordermanagement.dto.OrderDTO;
import org.ordermanagement.ordermanagement.dto.OrderResponseDTO;
import org.ordermanagement.ordermanagement.entity.Order;
import org.ordermanagement.ordermanagement.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
        log.info("Fetching orders for customer: {}", customerId);

        List<OrderResponseDTO> dtos = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("Creating new order: {}", orderDTO);
        Order order = orderService.createOrder(orderDTO);
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrder(order);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        log.info("Updating order status for order: {}", id);
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrder(orderService.updateOrderStatus(id, orderDTO.getStatus()));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByProductId(@PathVariable Long productId) {
        log.info("Fetching orders for product: {}", productId);
        List<OrderResponseDTO> responses = orderService.getOrdersByProductId(productId);
        return ResponseEntity.ok(responses);
    }
}