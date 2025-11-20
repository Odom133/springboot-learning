package com.balancika.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaleDetailsRequest {
    private Long id;
    private String itemName;
    private Integer quantity;
    private Double price;
    private Long saleId;
}
