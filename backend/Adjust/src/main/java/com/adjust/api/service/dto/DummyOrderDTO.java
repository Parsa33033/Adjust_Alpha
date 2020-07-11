package com.adjust.api.service.dto;

import com.adjust.api.domain.Cart;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * A DTO for the {@link com.adjust.api.domain.Order} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DummyOrderDTO extends OrderDTO implements Serializable {

    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
