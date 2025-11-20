package com.balancika.model.request;

import com.balancika.entity.Customer;
import com.balancika.model.dto.Sale.SaleDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
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

public class SaleRequest {
    private Long id;
    private Long customerId;
    private LocalDate saleDate;

    @JsonProperty("saleDetails")
    private List<SaleDetailsRequest> saleDetailsRequest;
}
