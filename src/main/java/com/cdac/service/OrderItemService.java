package com.cdac.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.entity.Order;
import com.cdac.entity.OrderItem;
import com.cdac.exception.OrderItemServiceException;
import com.cdac.repository.OrderItemRepository;
import com.cdac.repository.OrderRepository;

@Service
@Transactional
public class OrderItemService{
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Transactional
	 public OrderItem addOrUpdateItem(OrderItem orderItem) {
		Optional<OrderItem> orderItemCheck = orderItemRepository.findById(orderItem.getOrder_item_id());
        
            if (orderItemCheck.isPresent()) {
                orderItemCheck.get().setQuantity(orderItem.getQuantity());
	                return orderItemRepository.save(orderItemCheck.get());        
            }
        return orderItemRepository.save(orderItem);
    }

	public boolean delete(OrderItem orderItem) {
		Optional<OrderItem> orderCheck = orderItemRepository.findById(orderItem.getOrder_item_id());
		if(orderCheck.isPresent()) {
			orderItemRepository.delete(orderItem);
			return true;
		}
		else {
			return false;
		}
	}
}
