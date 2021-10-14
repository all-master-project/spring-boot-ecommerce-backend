package com.luv2code.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.dao.CustomerRepository;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.OrderItem;

@Service
public class CheckoutServiceImpl implements CheckoutService{
	
	
	private CustomerRepository customerRepository;
	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository=customerRepository;
	}
	
	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
		//retrieve the order info from dto
		Order orders = purchase.getOrder();
		
		//Generate tracking number
		String orderTrackingNumber= generateOrderTrackingNumber();
		orders.setOrderTrackingNumber(orderTrackingNumber );
		
		//populate order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item->orders.add(item));
		
		//populate order with billing address and shipping address
		orders.setBillingAddress(purchase.getBillingAddress());
		orders.setShippingAddress(purchase.getShippingAddress());
		
		//populate customer with order
		Customer customer = purchase.getCustomer();
		customer.add(orders);
		
		//save to the databsee
		customerRepository.save(customer);
		
		//return a response
		
		return new PurchaseResponse(orderTrackingNumber); 
	}

	private String generateOrderTrackingNumber() {
		
		//generate a randum UUID number (UUID version-4)
		//For details see: wikkepedia
		return UUID.randomUUID().toString();
	}
	
}
