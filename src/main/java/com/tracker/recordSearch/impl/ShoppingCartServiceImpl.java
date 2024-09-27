package com.tracker.recordSearch.impl;

import com.tracker.recordSearch.domain.CartItem;
import com.tracker.recordSearch.domain.Customer;
import com.tracker.recordSearch.domain.ShoppingCart;
import com.tracker.recordSearch.domain.Trip;
import com.tracker.recordSearch.dto.CartItemDto;
import com.tracker.recordSearch.dto.ShoppingCartDto;
import com.tracker.recordSearch.dto.TripDto;
import com.tracker.recordSearch.repository.CartItemRepository;
import com.tracker.recordSearch.repository.ShoppingCartRepository;
import com.tracker.recordSearch.services.CustomerService;
import com.tracker.recordSearch.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;

    private final CartItemRepository itemRepository;

    private final CustomerService customerService;
    @Override
    @Transactional
    public ShoppingCart addItemToCart(TripDto tripDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItemList, tripDto.getId());
        Trip trip = transfer(tripDto);

        double unitPrice = tripDto.getCostPrice();

        int itemQuantity = 0;
        if (cartItemList == null) {
            cartItemList = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setTrip(trip);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItem.setCart(shoppingCart);
                cartItemList.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                itemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setTrip(trip);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItem.setCart(shoppingCart);
                cartItemList.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                itemRepository.save(cartItem);
            }
        }
        shoppingCart.setCartItems(cartItemList);

        double totalPrice = totalPrice(shoppingCart.getCartItems());
        int totalItem = totalItem(shoppingCart.getCartItems());

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        shoppingCart.setCustomer(customer);

        return cartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart updateCart(TripDto tripDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem item = find(cartItemList, tripDto.getId());
        int itemQuantity = quantity;


        item.setQuantity(itemQuantity);
        itemRepository.save(item);
        shoppingCart.setCartItems(cartItemList);
        int totalItem = totalItem(cartItemList);
        double totalPrice = totalPrice(cartItemList);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        return cartRepository.save(shoppingCart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ShoppingCart removeItemFromCart(TripDto productDto, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem item = find(cartItemList, productDto.getId());
        cartItemList.remove(item);
        itemRepository.delete(item);
        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);
        shoppingCart.setCartItems(cartItemList);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        return cartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto addItemToCartSession(ShoppingCartDto cartDto, TripDto tripDto, int quantity) {
        CartItemDto cartItem = findInDTO(cartDto, tripDto.getId());
        if (cartDto == null) {
            cartDto = new ShoppingCartDto();
        }
        Set<CartItemDto> cartItemList = cartDto.getCartItems();
        double unitPrice = tripDto.getCostPrice();
        int itemQuantity = 0;
        if (cartItemList == null) {
            cartItemList = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItemDto();
                cartItem.setTrip(tripDto);
                cartItem.setCart(cartDto);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                System.out.println("add");
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItemDto();
                cartItem.setTrip(tripDto);
                cartItem.setCart(cartDto);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                System.out.println("add");
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
            }
        }
        System.out.println("here");
        cartDto.setCartItems(cartItemList);
        double totalPrice = totalPriceDto(cartItemList);
        int totalItem = totalItemDto(cartItemList);
        cartDto.setTotalPrice(totalPrice);
        cartDto.setTotalItems(totalItem);
        System.out.println(cartDto.getTotalItems());
        System.out.println(cartDto.getTotalPrice());
        System.out.println("success");
        return cartDto;
    }

    @Override
    public ShoppingCartDto updateCartSession(ShoppingCartDto cartDto, TripDto productDto, int quantity) {
        Set<CartItemDto> cartItemList = cartDto.getCartItems();
        CartItemDto item = findInDTO(cartDto, productDto.getId());
        int itemQuantity = item.getQuantity() + quantity;
        int totalItem = totalItemDto(cartItemList);
        double totalPrice = totalPriceDto(cartItemList);
        item.setQuantity(itemQuantity);
        cartDto.setCartItems(cartItemList);
        cartDto.setTotalPrice(totalPrice);
        cartDto.setTotalItems(totalItem);
        System.out.println(cartDto.getTotalItems());
        return cartDto;
    }

    @Override
    public ShoppingCartDto removeItemFromCartSession(ShoppingCartDto cartDto, TripDto tripDto, int quantity) {
        Set<CartItemDto> cartItemList = cartDto.getCartItems();
        CartItemDto item = findInDTO(cartDto, tripDto.getId());
        cartItemList.remove(item);
        double totalPrice = totalPriceDto(cartItemList);
        int totalItem = totalItemDto(cartItemList);
        cartDto.setCartItems(cartItemList);
        cartDto.setTotalPrice(totalPrice);
        cartDto.setTotalItems(totalItem);
        System.out.println(cartDto.getTotalItems());
        return cartDto;
    }

    @Override
    public ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart) {
        if (cart == null) {
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems == null) {
            cartItems = new HashSet<>();
        }
        Set<CartItem> cartItemsDto = convertCartItem(cartDto.getCartItems(), cart);
        for (CartItem cartItem : cartItemsDto) {
            cartItems.add(cartItem);
        }
        double totalPrice = totalPrice(cartItems);
        int totalItems = totalItem(cartItems);
        cart.setTotalItems(totalItems);
        cart.setCartItems(cartItems);
        cart.setTotalPrice(totalPrice);
        return cart;
    }

    @Override
    @Transactional
    public void deleteCartById(Long id) {
        ShoppingCart shoppingCart = cartRepository.getById(id);
        if(!ObjectUtils.isEmpty(shoppingCart) && !ObjectUtils.isEmpty(shoppingCart.getCartItems())){
            itemRepository.deleteAll(shoppingCart.getCartItems());
        }
        shoppingCart.getCartItems().clear();
        shoppingCart.setTotalPrice(0);
        shoppingCart.setTotalItems(0);
        cartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getCart(String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getCart();
        return cart;
    }


    private CartItem find(Set<CartItem> cartItems, long tripId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getTrip().getId() == tripId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private CartItemDto findInDTO(ShoppingCartDto shoppingCart, long tripId) {
        if (shoppingCart == null) {
            return null;
        }
        CartItemDto cartItem = null;
        for (CartItemDto item : shoppingCart.getCartItems()) {
            if (item.getTrip().getId() == tripId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItem(Set<CartItem> cartItemList) {
        int totalItem = 0;
        for (CartItem item : cartItemList) {
            totalItem += item.getQuantity();
        }
        return totalItem;
    }

    private double totalPrice(Set<CartItem> cartItemList) {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            totalPrice += item.getUnitPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    private int totalItemDto(Set<CartItemDto> cartItemList) {
        int totalItem = 0;
        for (CartItemDto item : cartItemList) {
            totalItem += item.getQuantity();
        }
        return totalItem;
    }

    private double totalPriceDto(Set<CartItemDto> cartItemList) {
        double totalPrice = 0;
        for (CartItemDto item : cartItemList) {
            totalPrice += item.getUnitPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    private Trip transfer(TripDto tripDto) {
        Trip trip = new Trip();
        trip.setId(tripDto.getId());
//        trip.setName(tripDto.getName());
        trip.setCurrentQuantity(tripDto.getCurrentQuantity());
        trip.setCostPrice(tripDto.getCostPrice());
        trip.setSalePrice(tripDto.getSalePrice());
        trip.setDescription(tripDto.getDescription());
        trip.setImage(tripDto.getImage());
        trip.set_activated(tripDto.isActivated());
        trip.set_deleted(tripDto.isDeleted());
        trip.setCategory(tripDto.getCategory());
        return trip;
    }

    private Set<CartItem> convertCartItem(Set<CartItemDto> cartItemDtos, ShoppingCart cart) {
        Set<CartItem> cartItems = new HashSet<>();
        for (CartItemDto cartItemDto : cartItemDtos) {
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItem.setTrip(transfer(cartItemDto.getTrip()));
            cartItem.setUnitPrice(cartItemDto.getUnitPrice());
            cartItem.setId(cartItemDto.getId());
            cartItem.setCart(cart);
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
