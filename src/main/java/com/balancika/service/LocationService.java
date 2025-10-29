package com.balancika.service;

import com.balancika.Exception.LocationExistedException;
import com.balancika.Exception.LocationNotFoundException;
import com.balancika.Exception.NotFoundException;
import com.balancika.Exception.WarehouseNotFoundException;
import com.balancika.entity.Location;
import com.balancika.entity.Warehouse;
import com.balancika.model.dto.LocationDTO;
import com.balancika.model.request.LocationCreateRequest;
import com.balancika.repository.LocationRepository;
import com.balancika.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public Page<LocationDTO> getAll(Pageable pageable) {
        Page<Location> locations = locationRepository.findAll(pageable);

        // fetch all warehouses once to avoid N+1 problem
        Map<Long, String> warehouseMap = warehouseRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Warehouse::getId, Warehouse::getName));

        return locations.map(loc -> LocationDTO.builder()
                .id(loc.getId())
                .name(loc.getName())
                .warehouseId(loc.getWarehouseId())
                .warehouseName(warehouseMap.getOrDefault(loc.getWarehouseId(), "Unknown"))
                .build());
    }

    @Transactional(readOnly = true)
    public LocationDTO getById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        String warehouseName = warehouseRepository.findById(location.getWarehouseId())
                .map(Warehouse::getName)
                .orElse("Unknown");

        return LocationDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .warehouseId(location.getWarehouseId())
                .warehouseName(warehouseName)
                .build();
    }

    @Transactional
    public LocationDTO create(LocationCreateRequest request) {
        // 1. Check if warehouse exists
        if (!warehouseRepository.existsById(request.getWarehouseId())) {
            throw new WarehouseNotFoundException(request.getWarehouseId());
        }

        // 2. Check duplicate location (same name + warehouseId)
        boolean exists = locationRepository.exists(
                Example.of(Location.builder()
                        .name(request.getName())
                        .warehouseId(request.getWarehouseId())
                        .build())
        );
        if (exists) {
            throw new LocationExistedException("Location already exists for this warehouse");
        }

        // 3. Save location
        Location saved = locationRepository.save(Location.builder()
                .name(request.getName())
                .warehouseId(request.getWarehouseId())
                .build());

        String warehouseName = warehouseRepository.findById(request.getWarehouseId())
                .map(Warehouse::getName)
                .orElse("Unknown");

        return LocationDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .warehouseId(saved.getWarehouseId())
                .warehouseName(warehouseName)
                .build();
    }

    @Transactional
    public LocationDTO update(Long id, LocationCreateRequest request) {
        // 1. Check if the location exists
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));

        // 2. check if warehouse exists
        boolean warehouseExists = warehouseRepository.existsById(request.getWarehouseId());
        if (!warehouseExists) {
            throw new WarehouseNotFoundException(request.getWarehouseId());
        }

        // 3. Check duplicate ( Same name + warehouseId but different ID)
        boolean duplicate = locationRepository.findAll(
                Example.of(Location.builder()
                        .name(request.getName())
                        .warehouseId(request.getWarehouseId())
                        .build())
        ).stream()
        .anyMatch(loc -> !loc.getId().equals(id));

        // 4. Update the existing location
        existing.setName(request.getName());
        existing.setWarehouseId(request.getWarehouseId());

        Location updated = locationRepository.save(existing);

        // 5. Map to DTO ( include warehouse name )
        String warehouseName = warehouseRepository.findById(request.getWarehouseId())
                .map(Warehouse::getName)
                .orElse("Unknown");

        return LocationDTO.builder()
                .id(updated.getId())
                .name(updated.getName())
                .warehouseId(updated.getWarehouseId())
                .warehouseName(warehouseName)
                .build();
    }

    // Option 1 for response exception
    @Transactional
    public void delete(Long id) {
        Location entity =locationRepository.findById(id).orElseThrow(() -> new NotFoundException("Location not found id : " +id));
        locationRepository.delete(entity);
    }

    /*
    // Option 2 for response exception
    @Transactional
    public void delete(Long id) {
        Location entity =locationRepository.findById(id).orElseThrow(NotFoundException::new);
        locationRepository.delete(entity);
    }

     */




}
