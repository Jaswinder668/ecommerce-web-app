package com.in.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in.ecommerce.entity.Orders;
import com.in.ecommerce.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

	List<Orders> findByUser(User user);

}
