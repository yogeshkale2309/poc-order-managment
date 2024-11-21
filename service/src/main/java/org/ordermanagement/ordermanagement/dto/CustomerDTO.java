package org.ordermanagement.ordermanagement.dto;



import java.util.List;

public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private List<OrderDTO> orders;
    private ShoppingCartDTO shoppingCart;
    public CustomerDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public ShoppingCartDTO getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartDTO shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public CustomerDTO(Long id, String name, String email, List<OrderDTO> orders, ShoppingCartDTO shoppingCart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.orders = orders;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", orders=" + orders +
                ", shoppingCart=" + shoppingCart +
                '}';
    }
}
