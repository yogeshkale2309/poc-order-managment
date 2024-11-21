package org.ordermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts={"classpath:data.sql"})
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetProductsByCustomerId() {
        String url = "http://localhost:" + port + "/api/products/customer/1";
        List response = restTemplate.getForObject(url, List.class);
        System.out.println(response);
        assert response.size() == 2;
        assertEquals(2, response.size());

    }
}
