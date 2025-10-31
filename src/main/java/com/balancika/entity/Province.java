package com.balancika.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "province",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "province_unique_check",
                    columnNames = {"name"}
            )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "var_desc")
    private String description;
}
