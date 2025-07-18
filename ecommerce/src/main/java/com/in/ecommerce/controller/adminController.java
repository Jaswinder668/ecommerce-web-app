package com.in.ecommerce.controller;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in.ecommerce.entity.Orders;
import com.in.ecommerce.entity.Product;
import com.in.ecommerce.entity.User;
import com.in.ecommerce.enums.OrderStatus;
import com.in.ecommerce.repository.OrderRepository;
import com.in.ecommerce.repository.ProductRepository;
import com.in.ecommerce.service.UserService;



@Controller
@RequestMapping("/admin")
public class adminController {
	
	@Autowired OrderRepository orderRepository;

	
	@Autowired private ProductRepository productRepository;
	@Autowired private UserService userService;
	
	@GetMapping("/getAllUsers")
	public String getAllUsers(Model model) {
	List<User> users=userService.getAllUser();
	model.addAttribute("users", users);
	return "admin/admin-users";	
	}
	
	
	@GetMapping("/dashboard")
	public String getAdminDashboard() {
		return "admin/adminDashboard";
	}
	
	@GetMapping("/showAllproducts")
	public String getAllProducts(Model model) {
		List<Product> products=productRepository.findAll();
		model.addAttribute("products", products);
		
		return "admin/showProducts";
	}
	 
	
	@GetMapping("/manage_orders")
	public String manageOrders(Model model) {
		
		
		model.addAttribute("ordersList",orderRepository.findAll());
		
		return "admin/manage_orders";
	}
	@PostMapping("/orders/update-status")
	public String updateOrderStatus(@RequestParam Long orderId,
	                                @RequestParam OrderStatus status) {

	    Orders order = orderRepository.findById(orderId)
	                  .orElseThrow(() -> new RuntimeException("Order not found"));

	    order.setStatus(status); // ✅ Set new status from dropdown
	    orderRepository.save(order);

	    return "redirect:/admin/manage_orders"; // or wherever your admin orders page is
	}
	
	@GetMapping("/product/delete/{id}")
    public String deleteProductByid(@PathVariable("id") Long id,RedirectAttributes redirectAttributes) {
		 Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
            
              if (!product.getOrderItems().isEmpty()) {
            	    redirectAttributes.addFlashAttribute("error", "Product is linked to orders and cannot be deleted.");
            	    return "redirect:/admin/showAllproducts";
            	}else {
            		  productRepository.delete(product);  
            	       return "redirect:/admin/showAllproducts";
            	}
              
       
    }
	


	
	


	

}
