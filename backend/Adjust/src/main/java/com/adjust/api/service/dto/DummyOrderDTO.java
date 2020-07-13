package com.adjust.api.service.dto;

import com.adjust.api.domain.Cart;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * A DTO for the {@link com.adjust.api.domain.Order} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DummyOrderDTO extends OrderDTO implements Serializable {

    private DummyCartDTO cart;

    public DummyCartDTO getCart() {
        return cart;
    }

    public void setCart(DummyCartDTO cart) {
        this.cart = cart;
    }
}
