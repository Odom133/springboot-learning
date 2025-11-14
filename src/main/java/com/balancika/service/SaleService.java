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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Transactional
    public Sale updateSale(Long id, SaleDTO dto) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sale not found : " + id));

        // update customer only if changed
        if (!sale.getCustomer().getId().equals(dto.getCustomerId())){
            Customer customer = customerRepository.findById(dto.getCustomerId())
                            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            sale.setCustomer(customer);
        }
        sale.setSaleDate(dto.getSaleDate());

        // Map existing details for quick lookup
        Map<Long, SaleDetails> existing = sale.getSaleDetails().stream()
                .collect(Collectors.toMap(SaleDetails::getId, d -> d ));

        List<SaleDetails> resultDetails = new ArrayList<>();

        for (SaleDetailDTO d : dto.getSaleDetails()) {
            // new detail

            if (d.getId() == null) {
                SaleDetails newDetail = new SaleDetails();
                newDetail.setItemName(d.getItemName());
                newDetail.setQuantity(d.getQuantity());
                newDetail.setPrice(d.getPrice());
                newDetail.setSale(sale);
                continue;
            }

            // Existing detail
            SaleDetails existingDetail = existing.get(d.getId());
            if (existingDetail != null) {
                boolean changed =
                        !Objects.equals(existingDetail.getItemName(), d.getItemName()) ||
                        !Objects.equals(existingDetail.getQuantity(), d.getQuantity()) ||
                        !Objects.equals(existingDetail.getPrice(), d.getPrice());

                if (changed) {
                    existingDetail.setItemName(d.getItemName());
                    existingDetail.setQuantity(d.getQuantity());
                    existingDetail.setPrice(d.getPrice());
                }

                resultDetails.add(existingDetail);
                existing.remove(d.getId());
            }
        }
        // Delete removed details
        existing.values().forEach(detail -> detail.setSale(null));

        // replace detail list
        sale.getSaleDetails().clear();
        resultDetails.forEach(sale::addSaleDetail);
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
