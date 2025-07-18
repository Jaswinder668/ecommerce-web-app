package com.in.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in.ecommerce.entity.OrderItems;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

}
