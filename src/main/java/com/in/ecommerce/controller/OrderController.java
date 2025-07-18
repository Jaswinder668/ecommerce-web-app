package com.in.ecommerce.controller;



import com.in.ecommerce.entity.Address;
import com.in.ecommerce.entity.Cart;
import com.in.ecommerce.entity.CartItem;
import com.in.ecommerce.entity.OrderItems;
import com.in.ecommerce.entity.Orders;
import com.in.ecommerce.entity.User;
import com.in.ecommerce.enums.OrderStatus;
import com.in.ecommerce.repository.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired  private UserRepository userRepository;

    @Autowired private CartRepository cartRepository;
    
    
    
    @Autowired private AddressRepository addressRepository;


    @Autowired private OrderRepository orderRepository;

  
    @GetMapping("/success")
    public String orderSuccess() {
        return "order_success";  // 🔁 HTML page: order-success.html
    }
    
  
    
    @GetMapping("/address")
    public String getAddressPage(Model model,Principal principal) {
    	model.addAttribute("Address", new Address());
    	String name=principal.getName();
    	User user=userRepository.findByEmail(name).get();
    	model.addAttribute("userid",user.getId());
    	return "checkout/address_page";
    }
    
    
  
    @PostMapping("/save_address")
    public String saveAddress(@ModelAttribute Address address, Model model) {
    	
    	addressRepository.save(address);
    	return "redirect:/order/getCheckoutPage";
    }
    @GetMapping("/addressDeleteById/{id}")
    public String deleteAddressByid(@PathVariable Long id) {
    	addressRepository.deleteById(id);
    	return "redirect:/order/getCheckoutPage";
    }
    

    @GetMapping("/myorders")
    public String viewMyOrders(Principal principal, Model model) {
        Optional<User> optionalUser = userRepository.findByEmail(principal.getName());
        User user;
        if(optionalUser.isPresent()) {
        	user=optionalUser.get();
        }else {
        	throw new RuntimeException("User not found in My Order Controller");
        }
        
        List<Orders> orders = orderRepository.findByUser(user);
        model.addAttribute("orders", orders);
        return "orders"; // 🔁 HTML page: orders.html
    }
    


    



    @GetMapping("/getCheckoutPage")
    public String checkoutPage(Model model,Principal principal) {
    	   User user = userRepository.findByEmail(principal.getName()).orElseThrow();
    	// 1. Fetch user's cart
   	    Cart cart = cartRepository.findByUser(user)
   	                 .orElseThrow(() -> new RuntimeException("Cart not found"));
        

           // Convert CartItems → Temp OrderItems for view only
           List<OrderItems> tempOrderItems = new ArrayList<>();
           double total = 0;

           for (CartItem item : cart.getCartItems()) {
               OrderItems oi = new OrderItems();
               oi.setProduct(item.getProduct());
               oi.setQuantity(item.getQuantity());
               oi.setTotalPrice(item.getTotalPrice());
               tempOrderItems.add(oi);
               total += item.getTotalPrice();
              
           }

           model.addAttribute("orderItems", tempOrderItems);
           model.addAttribute("totalAmount", total);
           model.addAttribute("user", user);
           model.addAttribute("listaddress", addressRepository.findByUser(user));
    
        return "checkout/checkoutPage"; // templates/checkout/checkoutPage.html
    }
    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id") Long orderId, RedirectAttributes redirectAttributes) {
        Optional<Orders> optionalOrder = orderRepository.findById(orderId);
        
        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();

            // Only allow cancel if NOT delivered or out for delivery
            if (order.getStatus() != OrderStatus.DELIVERED && order.getStatus() != OrderStatus.OUTFORDELIVERY) {
                orderRepository.delete(order);
                redirectAttributes.addFlashAttribute("message", "Order cancelled successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "You cannot cancel a delivered or out-for-delivery order.");
            }
        }
        return "redirect:/order/myorders";
    }

}




