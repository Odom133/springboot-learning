package com.balancika.controller;

import com.balancika.model.dto.PaginationDTO;
import com.balancika.model.dto.ProvinceDTO;
import com.balancika.model.request.ProvinceCreateRequest;
import com.balancika.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationDTO<ProvinceDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<ProvinceDTO> result = provinceService.getAll(pageable);

        return PaginationDTO.<ProvinceDTO>builder()
                .content(result.getContent())
                .pageNumber(page)
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ProvinceDTO getById(@PathVariable("id") Long id) {
        return provinceService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProvinceDTO create(@RequestBody ProvinceCreateRequest payload) {
        return provinceService.create(payload);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProvinceDTO update(@PathVariable("id") Long id, @RequestBody ProvinceCreateRequest payload) {
        return provinceService.update(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        provinceService.delete(id);
    }
}
