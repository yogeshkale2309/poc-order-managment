package org.ordermanagement;

import org.junit.jupiter.api.Test;
import org.ordermanagement.ordermanagement.dto.CustomerDTO;
import org.ordermanagement.ordermanagement.dto.ShoppingCartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts={"classpath:data.sql"})
public class ShoppingCartControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetCartDetails() {
        String url = "http://localhost:" + port + "/api/shopping-carts/cart/2";
        CustomerDTO shoppingCartResponseDTO =  restTemplate.getForObject(url, CustomerDTO.class);
        System.out.println(shoppingCartResponseDTO);
        assertTrue(!shoppingCartResponseDTO.getShoppingCart().getProducts().isEmpty());
    }
    @Test
    public void testAddToCart() {
        String url = "http://localhost:" + port + "/api/shopping-carts/1/add/4";
        ResponseEntity<ShoppingCartDTO> responseEntity = restTemplate.postForEntity(url, null, ShoppingCartDTO.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ShoppingCartDTO shoppingCartDTO = responseEntity.getBody();
        System.out.println(shoppingCartDTO);
        assertNotNull(shoppingCartDTO);
        assertEquals(1, shoppingCartDTO.getProducts().get(0).getId());
        assertEquals(1, shoppingCartDTO.getProducts().get(0).getId());
    }
    @Test
    public void testRemoveFromCart() {
        String url = "http://localhost:" + port + "/api/shopping-carts/1/remove/1";
        ResponseEntity<ShoppingCartDTO> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, ShoppingCartDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ShoppingCartDTO shoppingCartDTO = responseEntity.getBody();
        System.out.println(shoppingCartDTO);
        assertNotNull(shoppingCartDTO);
        assertEquals(1, shoppingCartDTO.getProducts().size());
        assertEquals(2, shoppingCartDTO.getProducts().get(0).getId());
    }
}
