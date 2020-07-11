package com.adjust.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.adjust.api.domain.AdjustShopingItem} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DummyShopingItemDTO extends ShopingItemDTO implements Serializable {


}
