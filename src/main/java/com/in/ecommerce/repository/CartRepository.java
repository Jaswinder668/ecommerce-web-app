package com.in.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.in.ecommerce.entity.Cart;
import com.in.ecommerce.entity.CartItem;
import com.in.ecommerce.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUser(Optional<User> user);

	Optional<Cart> findByUser(User user);
	@Query("SELECT c.cartItems FROM Cart c WHERE c.user = :user")
	List<CartItem> findCartItemsByUser(@Param("user") User user);


	



}
