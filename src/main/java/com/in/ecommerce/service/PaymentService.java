package com.in.ecommerce.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.ecommerce.entity.CartItem;
import com.in.ecommerce.entity.OrderItems;
import com.in.ecommerce.entity.Orders;
import com.in.ecommerce.entity.User;
import com.in.ecommerce.enums.OrderStatus;
import com.in.ecommerce.repository.AddressRepository;
import com.in.ecommerce.repository.CartItemRepsitory;
import com.in.ecommerce.repository.CartRepository;
import com.in.ecommerce.repository.OrderRepository;
import com.in.ecommerce.repository.UserRepository;

@Service
public class PaymentService {
	@Autowired private OrderRepository orderRepository;
	@Autowired private AddressRepository addressRepository;
	@Autowired private CartItemRepsitory cartItemRepsitory;
	@Autowired private CartRepository cartRepository;
	
	public Orders placeOrder(User user, Long addressId, String paymentMethod, List<CartItem> cartItems,
            String razorpayPaymentId, Double platformFee, Double finalAmount) {

List<OrderItems> orderItems = cartItems.stream().map(cartItem -> {
OrderItems item = new OrderItems();
item.setProduct(cartItem.getProduct());
item.setQuantity(cartItem.getQuantity());
item.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
return item;
}).collect(Collectors.toList());

Orders order = new Orders();
order.setItems(orderItems);
order.setUser(user);
order.setLocalDateTime(LocalDateTime.now());
order.setPaymentMethod(paymentMethod);
order.setPaymentId(razorpayPaymentId);
order.setStatus(OrderStatus.PLACED);
order.setTotalAmount(finalAmount); // Save final amount including fee
order.setPlatformFee(platformFee); // You can add this field in Orders entity
order.setAddress(addressRepository.findById(addressId).get());

orderRepository.save(order);

cartItemRepsitory.deleteAll(cartItems);
cartRepository.deleteAll();

return order;
}


}
