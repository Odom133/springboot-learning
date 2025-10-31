package com.balancika.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accountset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "int_sysid", nullable = false)
    private Long id;

    @Column(name = "var_type", nullable = false)
    private String type;

    @Column(name = "var_groupid", nullable = false)
    private String groupId;

    @Column(name = "var_subgroupid", nullable = false)
    private String subGroupId;

    @Column(name = "int_accid")
    private Long accountId;
}

