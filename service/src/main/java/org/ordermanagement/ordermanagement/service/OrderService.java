package org.ordermanagement.ordermanagement.service;

import org.ordermanagement.ordermanagement.dto.OrderDTO;
import org.ordermanagement.ordermanagement.dto.OrderResponseDTO;
import org.ordermanagement.ordermanagement.entity.Order;

import java.util.List;

public interface OrderService {
    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId);
    public Order createOrder(OrderDTO orderDTO);
    public Order updateOrderStatus(Long id, String status);
    public List<OrderResponseDTO> getOrdersByProductId(Long productId);
}
