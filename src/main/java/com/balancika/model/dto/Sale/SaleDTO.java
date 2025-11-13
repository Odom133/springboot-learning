package com.balancika.model.dto.Sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SaleDTO {
    private Long id;
    private Long customerId;
    private LocalDate saleDate;
    private List<SaleDetailDTO> saleDetails;

}
