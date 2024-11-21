package org.ordermanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ordermanagement.ordermanagement.dto.OrderDTO;
import org.ordermanagement.ordermanagement.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts={"classpath:data.sql"})
public class OrderControllerIntegrationTest {

    @LocalServerPort
    private int port;


    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testGetAllOrdersByCustomerId() {
        OrderResponseDTO expectedResponse = new OrderResponseDTO(2L, "SHIPPED", 2L, 2L);
        String url = "http://localhost:" + port + "/api/orders/customer/2";
        ResponseEntity<List<OrderResponseDTO>> orderResponseDTO = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderResponseDTO>>() {}
        );
        assertFalse(orderResponseDTO.getBody().isEmpty());
        assertEquals(expectedResponse, orderResponseDTO.getBody().get(0));
        assertEquals(HttpStatus.OK, orderResponseDTO.getStatusCode());

    }

    @Test
    public void testGetAllOrdersByProductId() {
        OrderResponseDTO expectedResponse = new OrderResponseDTO(1L, "NEW", 1L, 1L);
        String url = "http://localhost:" + port + "/api/orders/product/1";
        ResponseEntity<List<OrderResponseDTO>> orderResponseDTO = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderResponseDTO>>() {}
        );
        assertFalse(orderResponseDTO.getBody().isEmpty());
        assertEquals(expectedResponse, orderResponseDTO.getBody().get(0));
        assertEquals(HttpStatus.OK, orderResponseDTO.getStatusCode());
    }

    @Test
    public void testUpdateOrderStatus_PUT_200_OK() {
        String url = "http://localhost:" + port + "/api/orders/2";
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(2L);
        orderDTO.setStatus("CANCELLED");
        orderDTO.setCustomerId(4L);
        orderDTO.setProductId(4L);
        ResponseEntity<OrderResponseDTO> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(orderDTO, null), OrderResponseDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OrderResponseDTO response = responseEntity.getBody();
        System.out.println("response: "+response);
        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("CANCELLED", response.getStatus());
        assertEquals(2L, response.getCustomerId());
        assertEquals(2L, response.getProductId());
    }

    @Test
    public void testCreateOrder_POST_201_CREATED() {
        String url = "http://localhost:" + port + "/api/orders";
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(11L);
        orderDTO.setStatus("PENDING");
        orderDTO.setCustomerId(3L);
        orderDTO.setProductId(3L);
        ResponseEntity<OrderResponseDTO> responseEntity = restTemplate.postForEntity(url, orderDTO, OrderResponseDTO.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        OrderResponseDTO response = responseEntity.getBody();
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("PENDING", response.getStatus());
        assertEquals(3L, response.getCustomerId());
        assertEquals(3L, response.getProductId());
    }
}
