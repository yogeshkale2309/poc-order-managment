package org.ordermanagement.ordermanagement.serviceimpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import org.ordermanagement.ordermanagement.serviceimpl.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrdersByCustomerId_ShouldReturnListOfOrders() {
        Long customerId = 1L;
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);
        order.setStatus(StatusConstant.PENDING.toString());
        Customer customer = new Customer();
        customer.setId(1L);
        Product product = new Product();
        product.setId(1L);
        order.setCustomer(customer);
        order.setProduct(product);
        orders.add(order);

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(orderRepository.findAllByCustomerId(customerId)).thenReturn(orders);

        List<OrderResponseDTO> result = orderService.getOrdersByCustomerId(customerId);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(StatusConstant.PENDING.toString(), result.get(0).getStatus());
        assertEquals(1L, result.get(0).getCustomerId());
        assertEquals(1L, result.get(0).getProductId());
    }


    @Test
    void getOrdersByCustomerId_WhenNoOrdersFound_ShouldReturnEmptyList() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));

        when(orderRepository.findAllByCustomerId(customerId)).thenReturn(new ArrayList<>());

        List<OrderResponseDTO> result = orderService.getOrdersByCustomerId(customerId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createOrder_ShouldCreateNewOrderAndSaveToRepository() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(1L);
        orderDTO.setProductId(1L);

        Order order = new Order();
        order.setId(1L);
        order.setStatus(StatusConstant.PENDING.toString());

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(orderDTO);
        assertNotNull(result.getId());
        assertEquals(StatusConstant.PENDING.toString(), result.getStatus());
    }

    @Test
    void updateOrderStatus_WhenOrderNotFound_ShouldThrowException() {
        Long orderId = 1L;
        String newStatus = StatusConstant.SHIPPED.toString();

        when(orderRepository.findById(orderId)).thenThrow(new OrderServiceException("Couldn't find order"));

        assertThrows(OrderServiceException.class, () -> orderService.updateOrderStatus(orderId, newStatus));
    }
    @Test
    void updateOrderStatus_WhenOrderNotFound_ShouldThrowRuntimeException() {
        Long orderId = 1L;
        String newStatus = StatusConstant.SHIPPED.toString();

        when(orderRepository.findById(orderId)).thenThrow(new RuntimeException("Not Found"));

        assertThrows(RuntimeException.class, () -> orderService.updateOrderStatus(orderId, newStatus));
    }


    @Test
    void getOrdersByProductId_ShouldReturnListOfOrders() {
        Long productId = 1L;
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);
        order.setStatus(StatusConstant.PENDING.toString());
        Customer customer = new Customer();
        customer.setId(1L);
        Product product = new Product();
        product.setId(1L);
        order.setCustomer(customer);
        order.setProduct(product);
        orders.add(order);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(orderRepository.findAllByProductId(productId)).thenReturn(orders);

        List<OrderResponseDTO> result = orderService.getOrdersByProductId(productId);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(StatusConstant.PENDING.toString(), result.get(0).getStatus());
        assertEquals(1L, result.get(0).getCustomerId());
        assertEquals(1L, result.get(0).getProductId());
    }


    @Test
    void getOrdersByProductId_WhenNoOrdersFound_ShouldReturnEmptyList() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.findAllByProductId(productId)).thenReturn(new ArrayList<>());

        List<OrderResponseDTO> result = orderService.getOrdersByProductId(productId);

        assertTrue(result.isEmpty());
    }
    @Test
    void updateOrderStatus_ShouldUpdateOrderStatusAndReturnOrderWithUpdatedStatus() {
        Long orderId = 1L;
        String newStatus = StatusConstant.SHIPPED.toString();
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(StatusConstant.PENDING.toString());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrderStatus(orderId, newStatus);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(newStatus, result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void findOrderById_WhenOrderNotFound_ShouldThrowOrderServiceException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderServiceException.class, () -> orderService.findOrderById(orderId));
    }
    @Test
    void findOrderById_ShouldReturnOrderWhenOrderIsFound() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(StatusConstant.PENDING.toString());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.findOrderById(orderId);
        System.out.println(result);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(StatusConstant.PENDING.toString(), result.getStatus());
    }
    @Test
    void getOrdersByProductId_ShouldThrowExceptionWhenCustomerNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenThrow(new OrderServiceException("Couldn't find product: " + productId));

        assertThrows(OrderServiceException.class, () -> orderService.getOrdersByProductId(productId));
    }
    @Test
    void getOrdersByCustomerId_ShouldThrowExceptionWhenCustomerNotFound() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenThrow(new ResourceNotFoundException("Customer not found with id: " + customerId));

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrdersByCustomerId(customerId));
    }
    @Test
    public void testGetOrdersByCustomerId_CustomerNotFound() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrdersByCustomerId(customerId);
        });
        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
    }
    @Test
    public void testGetOrdersByProductId_CustomerNotFound() {
        Long customerId = 1L;
        when(productRepository.findById(customerId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(OrderServiceException.class, () -> {
            orderService.getOrdersByProductId(customerId);
        });
        assertEquals("Couldn't find product: " + customerId, exception.getMessage());
    }

}