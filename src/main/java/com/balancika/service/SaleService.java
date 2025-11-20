package com.balancika.service;

import com.balancika.Exception.ResourceNotFoundException;
import com.balancika.Mapper.SaleMapper;
import com.balancika.entity.Customer;
import com.balancika.entity.Sale;
import com.balancika.entity.SaleDetails;
import com.balancika.model.dto.Sale.SaleDTO;
import com.balancika.model.dto.Sale.SaleDetailDTO;
import com.balancika.model.dto.Sale.SaleResponseDTO;
import com.balancika.model.request.SaleDetailsRequest;
import com.balancika.model.request.SaleRequest;
import com.balancika.repository.CustomerRepository;
import com.balancika.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final SaleMapper saleMapper;

    @Transactional
    public Sale createSale(SaleDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(dto.getSaleDate());

        // add saleDetails

        if (dto.getSaleDetails() != null) {
            for (SaleDetailDTO detailDTO : dto.getSaleDetails()) {
                SaleDetails detail = new SaleDetails();
                detail.setItemName(detailDTO.getItemName());
                detail.setQuantity(detailDTO.getQuantity());
                detail.setPrice(detailDTO.getPrice());
                sale.addSaleDetail(detail);
            }
        }
        return saleRepository.save(sale);
    }

    @Transactional
    public void delete(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
        saleRepository.delete(sale);
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
                                .id(detail.getId())
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
                        .id(details.getId())
                        .itemName(details.getItemName())
                        .quantity(details.getQuantity())
                        .price(details.getPrice())
                        .build())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
