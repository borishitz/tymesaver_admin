package com.tracker.recordSearch.services;

import com.tracker.recordSearch.domain.Order;
import com.tracker.recordSearch.domain.ShoppingCart;

import java.util.List;


public interface OrderService {
    Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findALlOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);
}
