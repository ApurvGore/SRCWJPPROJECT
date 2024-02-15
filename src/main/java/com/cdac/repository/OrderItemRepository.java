package com.cdac.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entity.Item;
import com.cdac.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

   // Optional<OrderItem> findByItemIdAndOrderId(int item_id, int order_id);
	
//	Optional<OrderItem> findByOrderId(int orderId);
//
//    Optional<OrderItem> findByItemIdAndOrderId(int itemId, int orderId);

}

