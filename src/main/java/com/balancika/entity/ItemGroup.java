package com.balancika.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itemgroup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "var_id", nullable = false, unique = true)
    private String groupId;

    @Column(name = "var_name", nullable = false)
    private String name;

    @Column(name = "var_type")
    private String type; // "Inventory part" or "Non-inventory part"

    @Column(name = "var_desc")
    private String description;

    @Column(name = "tin_inactive", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean inactive = false;
}

