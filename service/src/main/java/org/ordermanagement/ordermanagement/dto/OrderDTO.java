package org.ordermanagement.ordermanagement.dto;


public class OrderDTO {
    private Long id;
    private String status;
    private Long customerId;
    private Long productId;

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
    public OrderDTO(Long id, String status, Long customerId, Long productId) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.productId = productId;
    }

    public OrderDTO() {

    }

}
