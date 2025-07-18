package com.in.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.in.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.product")
	List<Category> findAllWithProducts();

}
