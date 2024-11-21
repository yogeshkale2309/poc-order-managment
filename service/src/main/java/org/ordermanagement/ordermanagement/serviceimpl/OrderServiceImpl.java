package org.ordermanagement.ordermanagement.serviceimpl;

import org.ordermanagement.ordermanagement.constants.StatusConstant;
import org.ordermanagement.ordermanagement.dto.OrderDTO;
import org.ordermanagement.ordermanagement.dto.OrderResponseDTO;
import org.ordermanagement.ordermanagement.entity.Customer;
import org.ordermanagement.ordermanagement.entity.Order;
import org.ordermanagement.ordermanagement.entity.Product;
import org.ordermanagement.ordermanagement.exception.OrderServiceException;
import org.ordermanagement.ordermanagement.exception.ResourceNotFoundException;
import org.ordermanagement.ordermanagement.repos.CustomerRepository;
import org.ordermanagement.ordermanagement.repos.OrderRepository;
import org.ordermanagement.ordermanagement.repos.ProductRepository;
import org.ordermanagement.ordermanagement.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        log.info("getOrd orders by customer id: {}", customerId);
        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
            List<Order> orders = findOrdersByCustomerId(customer.getId());
            List<OrderResponseDTO> responses = new ArrayList<>();
            for (Order order : orders) {
                OrderResponseDTO response = new OrderResponseDTO();
                response.setId(order.getId());
                response.setStatus(order.getStatus());
                response.setCustomerId(order.getCustomer().getId());
                response.setProductId(order.getProduct().getId());
                responses.add(response);
            }
            return responses;
        }catch (ResourceNotFoundException e) {
            log.error("Error updating order status", e);
            throw e;
        }
    }

    private List<Order> findOrdersByCustomerId(Long customerId) {
        log.info("find orders by customer id: {}", customerId);
        return orderRepository.findAllByCustomerId(customerId);
    }
    @Modifying
    @Transactional
    @Override
    public Order createOrder(OrderDTO orderDTO) {
        log.info("create order: " + orderDTO);
        Order order = new Order();

        order.setStatus(String.valueOf(StatusConstant.PENDING));

        Customer customer = new Customer();
        customer.setId(orderDTO.getCustomerId());

        Product product = new Product();
        product.setId(orderDTO.getProductId());

        order.setCustomer(customer);
        order.setProduct(product);

        order.setId(orderDTO.getCustomerId());
        order.setId(orderDTO.getProductId());
        order.setStatus(String.valueOf(StatusConstant.PENDING));
        orderRepository.save(order);
        log.info("Order created successfully");
        return order;
    }
    @Modifying
    @Transactional
    @Override
    public Order updateOrderStatus(Long id, String status) {
        log.info("Update order status");
        try {
            Order order = findOrderById(id);
            order.setStatus(status);
            log.info("Order status updated successfully");
            return orderRepository.save(order);
        } catch (OrderServiceException e) {
            log.error("Error updating order status", e);
            throw e;
        }
    }


    public Order findOrderById(Long id) {
        log.info("Find order by id: " + id);
        try {
            return orderRepository.findById(id).orElseThrow(() -> new OrderServiceException("Couldn't find order: " + id));
        } catch (OrderServiceException e) {
            log.error("Error updating order status", e);
            throw e;
        }
    }

    @Override
    public List<OrderResponseDTO> getOrdersByProductId(Long productId) {
        log.info("getOrd orders by product id: " + productId);
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new OrderServiceException("Couldn't find product: " + productId));
            List<Order> orders = findOrdersByProductId(product.getId());
            List<OrderResponseDTO> responses = new ArrayList<>();
            for (Order order : orders) {
                OrderResponseDTO response = new OrderResponseDTO();
                response.setId(order.getId());
                response.setStatus(order.getStatus());
                response.setCustomerId(order.getCustomer().getId());
                response.setProductId(order.getProduct().getId());
                responses.add(response);
            }
            return responses;
        } catch (OrderServiceException e) {
            log.error("Error updating order status", e);
            throw e;
        }
    }

    private List<Order> findOrdersByProductId(Long productId) {
        log.info("find orders by product id: " + productId);
        return orderRepository.findAllByProductId(productId);
    }
}
