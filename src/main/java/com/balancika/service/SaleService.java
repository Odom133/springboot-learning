package com.balancika.service;

import com.balancika.Exception.ResourceNotFoundException;
import com.balancika.entity.Customer;
import com.balancika.entity.Sale;
import com.balancika.entity.SaleDetails;
import com.balancika.model.dto.Sale.SaleDTO;
import com.balancika.model.dto.Sale.SaleDetailDTO;
import com.balancika.repository.CustomerRepository;
import com.balancika.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Sale saveSale(SaleDTO dto) {
        // Fetch customer
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + dto.getCustomerId()));

        // Create sale
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(dto.getSaleDate());

        // Add sale details
        if (dto.getSaleDetails() != null) {
            for (SaleDetailDTO detailDTO : dto.getSaleDetails()) {
                SaleDetails detail = new SaleDetails();
                detail.setItemName(detailDTO.getItemName());
                detail.setQuantity(detailDTO.getQuantity());
                detail.setPrice(detailDTO.getPrice());
                sale.addSaleDetail(detail); // sets sale reference
            }
        }

        // Save and return
        return saleRepository.save(sale);
    }

    @Transactional(readOnly = true)
    public Page<SaleDTO> getAll(Pageable pageable) {
        Page<Sale> sales = saleRepository.findAll(pageable);
        return sales.map(sale -> SaleDTO.builder()
                .id(sale.getId())
                .customerId(sale.getCustomer().getId())
                .saleDate(sale.getSaleDate())
                .saleDetails(sale.getSaleDetails().stream()
                        .map(detail -> SaleDetailDTO.builder()
                                .itemName(detail.getItemName())
                                .quantity(detail.getQuantity())
                                .price(detail.getPrice())
                        .build())
                        .collect(Collectors.toList()))
                .build());
    }

    @Transactional(readOnly = true)
    public SaleDTO getById(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        return SaleDTO.builder()
                .id(sale.getId())
                .customerId(sale.getCustomer().getId())
                .saleDate(sale.getSaleDate())
                .saleDetails(sale.getSaleDetails().stream()
                        .map(details -> SaleDetailDTO.builder()
                        .itemName(details.getItemName())
                        .quantity(details.getQuantity())
                        .price(details.getPrice())
                        .build())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
