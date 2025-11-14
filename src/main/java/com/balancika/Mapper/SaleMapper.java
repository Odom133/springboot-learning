package com.balancika.Mapper;

import com.balancika.entity.Customer;
import com.balancika.entity.Sale;
import com.balancika.model.dto.CustomerDTO;
import com.balancika.model.dto.Sale.SaleDetailDTO;
import com.balancika.model.dto.Sale.SaleResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleMapper {

    public SaleResponseDTO toResponseDTO(Sale sale) {
        Customer customer = sale.getCustomer();
        CustomerDTO customerDTO = new CustomerDTO(
                customer.getId(), customer.getName(),customer.getDescription(), customer.getEmail(), customer.getPhone()
        );

        List<SaleDetailDTO> detailDTOs = sale.getSaleDetails().stream()
                .map(detail -> new SaleDetailDTO(detail.getId(), detail.getItemName(), detail.getQuantity(), detail.getPrice()))
                .collect(Collectors.toList());

        return new SaleResponseDTO(sale.getId(), sale.getSaleDate(), customerDTO, detailDTOs);
    }
}

