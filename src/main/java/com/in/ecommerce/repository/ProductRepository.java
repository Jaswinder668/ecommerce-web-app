package com.in.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.in.ecommerce.entity.Category;
import com.in.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
	List<Product> findByCategory_Id(Long id);

	List<Product> findByCategory_Id(Category id);



	// If you want to search by product name:
	List<Product> findByNameContainingIgnoreCase(String name);





}
