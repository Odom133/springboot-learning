package com.balancika.repository;

import com.balancika.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
boolean existsByName(String name);
    boolean existsByNameAndId(String name, Long id);
}
