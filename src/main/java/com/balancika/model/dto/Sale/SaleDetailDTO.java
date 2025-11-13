package com.balancika.model.dto.Sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SaleDetailDTO {
    private String itemName;
    private Integer quantity;
    private Double price;

}
