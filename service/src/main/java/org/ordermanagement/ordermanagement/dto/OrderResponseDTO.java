package org.ordermanagement.ordermanagement.dto;


import org.ordermanagement.ordermanagement.entity.Order;

import java.util.Objects;

public class OrderResponseDTO {
    private Long id;
    private String status;
    private Long customerId;
    private Long productId;

    public OrderResponseDTO() {

    }

    public OrderResponseDTO(Long id, String status, Long customerId, Long productId) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.productId = productId;
    }

    public OrderResponseDTO(Order order) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public void setOrder(Order order) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.customerId = order.getCustomer().getId();
        this.productId = order.getProduct().getId();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponseDTO that = (OrderResponseDTO) o;
        return id == that.id &&
                customerId == that.customerId &&
                productId == that.productId &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, customerId, productId);
    }

    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", customerId=" + customerId +
                ", productId=" + productId +
                '}';
    }
}

