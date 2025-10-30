package com.balancika.repository;

import com.balancika.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Page<Warehouse> findAll(Pageable pageable);
    boolean existsByName(String name);
    boolean existsById(Long id);
}
