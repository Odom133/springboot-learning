package com.balancika.service;

import com.balancika.Exception.NotFoundException;
import com.balancika.entity.Warehouse;
import com.balancika.model.dto.WarehouseDTO;
import com.balancika.model.request.WarehouseCreateRequest;
import com.balancika.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public List<WarehouseDTO> getAll() {
        return warehouseRepository.findAll()
                .stream()
                .map(warehouse -> WarehouseDTO.builder()
                        .id(warehouse.getId())
                        .name(warehouse.getName())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public WarehouseDTO getById(Long id) {
        return warehouseRepository.findById(id).map(WarehouseDTO::new).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public WarehouseDTO create(WarehouseCreateRequest payload ) {
        return new WarehouseDTO(warehouseRepository.save(Warehouse.builder()
                .name(payload.getName())
                .build()));
    }
}
