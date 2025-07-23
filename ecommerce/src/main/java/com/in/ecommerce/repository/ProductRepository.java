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



	 List<Product> findByNameContainingIgnoreCase(String keyword);
	  // Related products from same category, excluding current product
	    List<Product> findTop4ByCategoryAndIdNot(Category category, Long id);
	    List<Product> findTop4ByCategory(Category category);
	    





}
