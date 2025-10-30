package com.balancika.controller;

import com.balancika.model.dto.PaginationDTO;
import com.balancika.model.dto.WarehouseDTO;
import com.balancika.model.request.WarehouseCreateRequest;
import com.balancika.repository.WarehouseRepository;
import com.balancika.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationDTO<WarehouseDTO> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<WarehouseDTO> result = warehouseService.getAll(pageable);

        return PaginationDTO.<WarehouseDTO>builder()
                .content(result.getContent())
                .pageNumber(page)
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public WarehouseDTO getById(@PathVariable("id") Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDTO create(@RequestBody WarehouseCreateRequest payload) {
        return warehouseService.create(payload);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WarehouseDTO update(@PathVariable("id") Long id, @RequestBody WarehouseCreateRequest payload) {
        return warehouseService.update(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        warehouseService.delete(id);
    }


}
