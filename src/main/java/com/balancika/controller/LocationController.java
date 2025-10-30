package com.balancika.controller;

import com.balancika.model.dto.LocationDTO;
import com.balancika.model.dto.PaginationDTO;
import com.balancika.model.request.LocationCreateRequest;
import com.balancika.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationDTO<LocationDTO> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<LocationDTO> result = locationService.getAll(pageable);

        return PaginationDTO.<LocationDTO>builder()
                .content(result.getContent())
                .pageNumber(page)
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public LocationDTO getById(@PathVariable("id") Long id) {
        return locationService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDTO create(@RequestBody LocationCreateRequest payload) {
        return locationService.create(payload);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDTO update(@PathVariable("id") Long id, @RequestBody LocationCreateRequest payload ) {
        return locationService.update(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        locationService.delete(id);
    }
}
