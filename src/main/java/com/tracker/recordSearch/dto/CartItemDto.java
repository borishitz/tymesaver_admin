package com.tracker.recordSearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;

    private ShoppingCartDto cart;

    private TripDto trip;

    private int quantity;

    private double unitPrice;

}
