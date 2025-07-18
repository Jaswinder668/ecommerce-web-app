package com.in.ecommerce.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.in.ecommerce.entity.Category;
import com.in.ecommerce.entity.Product;
import com.in.ecommerce.entity.ProductSpecification;
import com.in.ecommerce.repository.CategoryRepository;
import com.in.ecommerce.repository.ProductRepository;
import com.in.ecommerce.service.ProductService;


@Controller
public class HomeController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired 
	ProductRepository productRepository;
	


	@GetMapping("/")
	public String index_Page(Model model) {
		List<Category> list = categoryRepository.findAllWithProducts();
		System.out.print(list);
	    model.addAttribute("category", list); // ✅ Key is "category"
	    return "index";
	}
	@GetMapping("/index")
	public String indexPage(Model model) {
		List<Category> list = categoryRepository.findAllWithProducts();
		System.out.print(list);
	    model.addAttribute("category", list); // ✅ Key is "category"
	    return "index";
	}
	    

}
