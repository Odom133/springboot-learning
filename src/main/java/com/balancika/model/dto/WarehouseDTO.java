package com.balancika.model.dto;

import com.balancika.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseDTO {
    private Long id;
    private String name;
    private Long provinceId;
    private String provinceName;

    public WarehouseDTO(Warehouse warehouse){
        this.id = warehouse.getId();
        this.name = warehouse.getName();
        this.provinceId = warehouse.getProvinceId();
    }
}
