package com.balancika.model.request;

import com.balancika.model.dto.Sale.SaleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SaleRequest {
    private List<SaleDTO> sales;
}
