package com.balancika.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(
        name = "warehouse",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "warehouse_unique_check",
                        columnNames = {"name", "province_id"}
                )
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "province_id")
    private Long provinceId;

}
