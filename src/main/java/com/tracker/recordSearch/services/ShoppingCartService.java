package com.tracker.recordSearch.services;

import com.tracker.recordSearch.domain.ShoppingCart;
import com.tracker.recordSearch.dto.ShoppingCartDto;
import com.tracker.recordSearch.dto.TripDto;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(TripDto tripDto, int quantity, String username);

    ShoppingCart updateCart(TripDto tripDto, int quantity, String username);

    ShoppingCart removeItemFromCart(TripDto tripDto, String username);

    ShoppingCartDto addItemToCartSession(ShoppingCartDto cartDto, TripDto tripDto, int quantity);

    ShoppingCartDto updateCartSession(ShoppingCartDto cartDto, TripDto tripDto, int quantity);

    ShoppingCartDto removeItemFromCartSession(ShoppingCartDto cartDto, TripDto tripDto, int quantity);

    ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);


    void deleteCartById(Long id);

    ShoppingCart getCart(String username);
}
