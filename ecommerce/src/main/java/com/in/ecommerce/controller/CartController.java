package com.in.ecommerce.controller;

import java.security.Principal;
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

import com.in.ecommerce.entity.Cart;
import com.in.ecommerce.entity.CartItem;
import com.in.ecommerce.entity.Product;
import com.in.ecommerce.entity.User;
import com.in.ecommerce.repository.CartItemRepsitory;
import com.in.ecommerce.repository.CartRepository;
import com.in.ecommerce.repository.ProductRepository;
import com.in.ecommerce.repository.UserRepository;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartItemRepsitory  cartItemRepository;
	
	  @GetMapping("/cartPage")
	    public String getCartPage(Principal principal,Model model) {
		 User user= userRepository.findByEmail(principal.getName()).orElseThrow(()->new RuntimeException("User not found"));
		 //Get Cart
		Cart cart= user.getCart();
		if(cart==null||cart.getCartItems().isEmpty()) {
			model.addAttribute("message", "Your cart is empty!");
			return "cart";
		}
		   model.addAttribute("cart", cart);
		  model.addAttribute("cartItems", cart.getCartItems());
	    	return "cart";
	    }
	    
	  @PostMapping("/add/cart/{productid}")
	    public String cartPage(@PathVariable("productid") Long id, @RequestParam(defaultValue = "1") int quantity,Principal principal, Model model
	    		,RedirectAttributes redirectAttributes) {
		  if (principal == null) {
		        return "redirect:/login";  // 👈 Safe fallback
		    }
		  User user = userRepository.findByEmail(principal.getName())
	                .orElseThrow(() -> new RuntimeException("User not found")); 
		  
	    	Product product=productRepository.findById(id)
	    			.orElseThrow(()->new RuntimeException("Product id not found"));
	    	
	    	
	    	Cart cart=user.getCart();
	    	if(cart==null) {
	    		cart=new Cart();
	    		cart.setUser(user);
	    		user.setCart(cart);
	    		
	    	}
	    	
	    	CartItem cartItem = new CartItem();
	    	cartItem.setProduct(product); // ✅ Set actual product
	    	cartItem.setQuantity(quantity); // ✅ Must be >= 1
	    	cartItem.setTotalPrice(product.getPrice()*quantity); // ✅
	    
	    	cartItem.setCart(cart);
	    	cart.getCartItems().add(cartItem);
	    	
	    	Double total=0.0;
	    	for(CartItem items:cart.getCartItems()) {
	    		total=total+items.getTotalPrice();
	    		
	    	}
	    	// ✅ Add platform fee
	    
	    	cart.setTotalprice(total);
	    	
	    	

	    
	    	cartRepository.save(cart);
	        redirectAttributes.addFlashAttribute("message", "Add Product to Cart successfully!");
	    	
	    	return "redirect:/cart/cartPage";

	    	
	    }
	  @PostMapping("/updateQuantity")
	  public String updateQuantity(@RequestParam Long cartItemId,
	                               @RequestParam String action,
	                               Principal principal) {
	      CartItem item = cartItemRepository.findById(cartItemId)
	          .orElseThrow(() -> new RuntimeException("Item not found"));

	      if (action.equals("increase")) {
	          item.setQuantity(item.getQuantity() + 1);
	      } else if (action.equals("decrease") && item.getQuantity() > 1) {
	          item.setQuantity(item.getQuantity() - 1);
	      }
           
	      item.setTotalPrice(item.getProduct().getPrice() * item.getQuantity());
	      cartItemRepository.save(item);
	      return "redirect:/cart/cartPage";
	  }
	  @PostMapping("/cartItems/deleteByid/{id}")
	  public String  deleteById(@PathVariable Long id,Principal principal) {
		  String email = principal.getName();
		    User user = userRepository.findByEmail(email).orElseThrow();

		 // 1. Fetch user's cart
		    Cart cart = cartRepository.findByUser(user)
		                 .orElseThrow(() -> new RuntimeException("Cart not found"));

		    // 1️⃣ Remove CartItem
		    CartItem itemToRemove = cartItemRepository.findById(id)
		                              .orElseThrow(() -> new RuntimeException("Item not found"));

		    cart.getCartItems().remove(itemToRemove);
		    cartItemRepository.delete(itemToRemove); // Important

		    // 2️⃣ Recalculate Total Price
		    double newTotal = 0.0;
		    for (CartItem item : cart.getCartItems()) {
		        newTotal += item.getTotalPrice(); // quantity * price
		    }
		    cart.setTotalprice(newTotal);

		    // 3️⃣ Save updated cart
		    cartRepository.save(cart);

		  
		  return "redirect:/cart/cartPage";
		  
	  }


}
