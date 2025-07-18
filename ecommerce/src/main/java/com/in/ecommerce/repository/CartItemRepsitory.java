package com.in.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in.ecommerce.entity.Cart;
import com.in.ecommerce.entity.CartItem;

public interface CartItemRepsitory extends JpaRepository<CartItem,Long> {
	List<CartItem> findByCart(Cart cart);

}
