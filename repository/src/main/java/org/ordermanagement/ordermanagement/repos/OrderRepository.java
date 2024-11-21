package org.ordermanagement.ordermanagement.repos;

import org.ordermanagement.ordermanagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findAllByCustomerId(Long customerId);

    List<Order> findAllByProductId(Long productId);
}
