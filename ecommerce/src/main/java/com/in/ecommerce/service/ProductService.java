package com.in.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.in.ecommerce.entity.Product;

import com.in.ecommerce.repository.ProductRepository;

@Service

public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}
	public Product addProduct(@ModelAttribute Product product) {
		// TODO Auto-generated method stub
        productRepository.save(product);
 return product;
		
		
	}
	public Product productGetByID(Long id) {
		// TODO Auto-generated method stub
		Product product= productRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Product Not Found"));
		return product;
	}

}
