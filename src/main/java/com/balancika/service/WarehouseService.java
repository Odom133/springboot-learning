package com.balancika.service;

import com.balancika.Exception.ResourceNotFoundException;
import com.balancika.entity.Province;
import com.balancika.entity.Warehouse;
import com.balancika.model.dto.WarehouseDTO;
import com.balancika.model.request.WarehouseCreateRequest;
import com.balancika.repository.ProvinceRepository;
import com.balancika.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final ProvinceRepository provinceRepository;

    @Transactional(readOnly = true)
    public Page<WarehouseDTO> getAll(Pageable pageable) {
        Page<Warehouse> warehouses = warehouseRepository.findAll(pageable);
        // fetch all province once to avoid N+1 problem
        Map<Long, String> provinceMap = provinceRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Province::getId, Province::getName));
        return  warehouses
                .map(warehouse -> WarehouseDTO.builder()
                        .id(warehouse.getId())
                        .name(warehouse.getName())
                        .provinceId(warehouse.getProvinceId())
                        .provinceName(provinceMap.getOrDefault(warehouse.getProvinceId(), "Unknow"))
                        .build());
    }

    @Transactional(readOnly = true)
    public WarehouseDTO getById(Long id) {
        // check warehouse not found
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found ID: "+id));
        // Map provinceName from Province entity
        String provinceName = provinceRepository.findById(warehouse.getProvinceId())
                .map(Province::getName)
                .orElse("Unknow");
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .provinceId(warehouse.getProvinceId())
                .provinceName(provinceName)
                .build();


    }

    @Transactional
    public WarehouseDTO create(WarehouseCreateRequest payload ) {
        // check province not found
        if(!provinceRepository.existsById(payload.getProvinceId())){
            throw new ResourceNotFoundException("Province not found ID: "+ payload.getProvinceId());
        }
        // check duplicate location ( same Name + warehouseId )
        boolean exists = warehouseRepository.exists(
            Example.of(Warehouse.builder()
                    .name(payload.getName())
                    .provinceId(payload.getProvinceId())
                    .build())
        );
        if (exists) {
           throw new DuplicateKeyException(
             String.format("Warehouse with name '%s' already exists in province ID %d",
                     payload.getName(), payload.getProvinceId())
           );
        }
        // Save warehouse
        Warehouse saved = warehouseRepository.save(Warehouse.builder()
                .name(payload.getName())
                .provinceId(payload.getProvinceId())
                .build());
        // Map provinceName
        String provinceName = provinceRepository.findById(payload.getProvinceId())
                .map(Province::getName)
                .orElse("Unknow");

        return WarehouseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .provinceId(saved.getProvinceId())
                .provinceName(provinceName)
                .build();
    }

    @Transactional
    public WarehouseDTO update(Long id, WarehouseCreateRequest payload) {

        // check warehouse not found
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found ID: "+id));

        // check province not found
        provinceRepository.findById(payload.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found ID : " + payload.getProvinceId()));

       // check duplicate ( same Name + provinceId )
        boolean duplicate = warehouseRepository.exists(
                Example.of(Warehouse.builder()
                        .name(payload.getName())
                        .provinceId(payload.getProvinceId())
                        .build())
        );
        if (duplicate && !(warehouse.getName().equals(payload.getName()) &&
                            warehouse.getProvinceId().equals(payload.getProvinceId()))){
            throw new DuplicateKeyException(
                    String.format("Warehouse with name '%s' already exists in province ID %d",
                            payload.getName(), payload.getProvinceId())
            );
        }

        // Update the existing warehouse
        warehouse.setName(payload.getName());
        warehouse.setProvinceId(payload.getProvinceId());

        Warehouse updated = warehouseRepository.save(warehouse);

        // Map warehouseName in WarehouseDTO
        String provinceName = provinceRepository.findById(payload.getProvinceId())
                .map(Province::getName)
                .orElse("Unknow");

        return WarehouseDTO.builder()
                .id(updated.getId())
                .name(updated.getName())
                .provinceId(updated.getProvinceId())
                .provinceName(provinceName)
                .build();
    }

    @Transactional
    public void delete(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found ID: "+id));
        warehouseRepository.delete(warehouse);
    }
}
