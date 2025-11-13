package com.balancika.model.dto.Sale;

import com.balancika.model.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponseDTO {
    private Long id;
    private LocalDate saleDate;
    private CustomerDTO customer;
    private List<SaleDetailDTO> saleDetails;
}


