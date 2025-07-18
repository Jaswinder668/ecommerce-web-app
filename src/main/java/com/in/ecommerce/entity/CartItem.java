package com.in.ecommerce.entity;


import jakarta.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private double totalprice=0.0;

    // 🔗 ManyToOne with Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // 🔗 ManyToOne with Cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // === Getters and Setters ===
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalprice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalprice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

