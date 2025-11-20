package com.balancika.controller;

import com.balancika.Mapper.SaleMapper;
import com.balancika.entity.Sale;
import com.balancika.model.dto.PaginationDTO;
import com.balancika.model.dto.Sale.SaleDTO;
import com.balancika.model.dto.Sale.SaleDetailDTO;
import com.balancika.model.dto.Sale.SaleResponseDTO;
import com.balancika.model.request.SaleRequest;
import com.balancika.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final SaleMapper saleMapper;

    @PostMapping
    public SaleResponseDTO createSale(@RequestBody SaleDTO request) {
        Sale savedSale = saleService.createSale(request);
        return saleMapper.toResponseDTO(savedSale);


    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> updateSale(
            @PathVariable Long id,
            @RequestBody SaleRequest request) {

        SaleResponseDTO updatedSale = saleService.updateSale(id, request);
        return ResponseEntity.ok(updatedSale);
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        saleService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationDTO<SaleDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1 , size, Sort.by("id").descending());
        Page<SaleDTO> result = saleService.getAll(pageable);

        return PaginationDTO.<SaleDTO>builder()
                .content(result.getContent())
                .pageNumber(page)
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SaleDTO getById(@PathVariable("id") Long id) {
        return saleService.getById(id);
    }



}
