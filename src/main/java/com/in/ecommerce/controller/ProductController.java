package com.in.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.in.ecommerce.entity.Category;
import com.in.ecommerce.entity.Product;
import com.in.ecommerce.entity.ProductSpecification;
import com.in.ecommerce.repository.CategoryRepository;
import com.in.ecommerce.repository.ProductRepository;
import com.in.ecommerce.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@PostMapping("/add")
	public String addproduct(@ModelAttribute Product product,@RequestParam("imageFile") MultipartFile file) throws IllegalStateException, IOException {
	    String fileName = file.getOriginalFilename();
		Category cat=categoryRepository.findById(product.getCategory().getId())
		.orElseThrow();
		product.setCategory(cat);
		
		for(ProductSpecification spec:product.getSpecifications()) {
			spec.setProduct(product);
		}
		product.setImageUrl(fileName);
		
		productRepository.save(product);
		 
		 return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/getproductbyid/{id}")
	public String getById(@PathVariable Long id,Model model) {
		Product product= productService.productGetByID(id);
		model.addAttribute("product", product);
		model.addAttribute("specification", product.getSpecifications());
		
		return "productDetails";
	}
	
	
	
	 @GetMapping("/showProductPage")
	   public String addProductPage(Model model) {
		   
		   Product p=new Product();
		   model.addAttribute("product", p);
		   for (int i = 0; i < 2; i++) {
		        p.getSpecifications().add(new ProductSpecification());
		    }
		    model.addAttribute("categories", categoryRepository.findAll()); // Category dropdown ke liye

		   
		   return "addProduct";
	   }
	 @GetMapping("/search")
	  @ResponseBody
	    public String searchProducts(@RequestParam("query") String query,Model model) {
	        List<Product> products= productRepository.findByNameContainingIgnoreCase(query);
	        model.addAttribute("products", products);
			return "productDetails";
	    }

	
	

	

}
