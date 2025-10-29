package com.balancika.repository;

import com.balancika.entity.Province;
import com.balancika.model.dto.ProvinceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
//    @Query("SELECT p FROM Province p WHERE p.id LIKE ?1% OR p.name LIKE %?2%")
    Page<Province> findAll(Pageable pageable);

    boolean existsByName(String name);
//    boolean existsByNameAndIdNot(String name,Long id);
}
