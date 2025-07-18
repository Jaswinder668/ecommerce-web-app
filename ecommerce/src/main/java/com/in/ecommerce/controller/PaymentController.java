package com.in.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.in.ecommerce.entity.Cart;
import com.in.ecommerce.entity.CartItem;
import com.in.ecommerce.entity.OrderItems;
import com.in.ecommerce.entity.Orders;
import com.in.ecommerce.entity.User;
import com.in.ecommerce.repository.CartItemRepsitory;
import com.in.ecommerce.repository.CartRepository;
import com.in.ecommerce.repository.OrderItemRepository;
import com.in.ecommerce.repository.UserRepository;
import com.in.ecommerce.service.PaymentService;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {
	

	
	
	@Autowired private  UserRepository userRepository;
	@Autowired private PaymentService paymentService;
	@Autowired private CartItemRepsitory cartItemRepsitory;
	@Autowired private CartRepository cartRepository;
	
	@PostMapping("/payment-success")
	public String paymentSuccess(
	        @RequestParam("paymentMethod") String paymentMethod,
	        @RequestParam("razorpayPaymentId") String razorpayPaymentId,
	        @RequestParam("addressId") Long addressId,
	        @RequestParam("platformFee") Double platformFee,
	        @RequestParam("finalAmount") Double finalAmount,
	        Principal principal) {

	    User user = userRepository.findByEmail(principal.getName())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<CartItem> cartItems = cartItemRepsitory.findByCart(user.getCart());

	    Orders order = paymentService.placeOrder(user, addressId, paymentMethod, cartItems, razorpayPaymentId, platformFee, finalAmount);

	    return "redirect:/order/success";
	}

	
	
	


}
