package com.balancika.model.dto;

import com.balancika.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDTO {
    private Long id;
    private String name;
    private Long warehouseId;
    private String warehouseName;

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.warehouseId = location.getWarehouseId();
    }
}
